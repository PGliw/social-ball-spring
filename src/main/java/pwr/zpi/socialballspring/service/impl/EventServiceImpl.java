package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.config.IdentityManager;
import pwr.zpi.socialballspring.dto.EventDto;
import pwr.zpi.socialballspring.dto.MatchProtocolDto;
import pwr.zpi.socialballspring.dto.Response.EventResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.*;
import pwr.zpi.socialballspring.repository.AcquaitanceDao;
import pwr.zpi.socialballspring.repository.EventDao;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.MatchMemberDao;
import pwr.zpi.socialballspring.service.EventService;
import pwr.zpi.socialballspring.util.Constants;
import pwr.zpi.socialballspring.util.dateUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "eventService")
public class EventServiceImpl implements EventService {
    @Autowired
    EventDao eventDao;

    @Autowired
    AcquaitanceDao acquaitanceDao;

    @Autowired
    IdentityManager identityManager;

    @Autowired
    MatchMemberDao matchMemberDao;

    @Autowired
    FootballMatchDao footballMatchDao;

    @Override
    public List<EventResponse> findAll(Optional<Boolean> forAcquaitance) {
        List<Event> list = new ArrayList<>();
        eventDao.findAll().iterator().forEachRemaining(list::add);
        if(forAcquaitance.isPresent() && forAcquaitance.get()){
            List<Acquaintance> acquaintances = new ArrayList<>();
            acquaitanceDao.findAll().iterator().forEachRemaining(acquaintances::add);
            List<User> acquaitanceUsers = Stream.concat(acquaintances.stream()
                            .filter(a -> a.getRequestSender().getId().equals(identityManager.getCurrentUser().getId()))
                            .filter(a -> a.getStatus().equals("accepted"))
                            .map(a -> a.getRequestReceiver()),
                        acquaintances.stream()
                            .filter(a -> a.getRequestReceiver().getId().equals(identityManager.getCurrentUser().getId()))
                            .filter(a -> a.getStatus().equals("accepted"))
                            .map(a -> a.getRequestSender()))
                    .distinct()
                    .filter(u -> !u.getId().equals(identityManager.getCurrentUser().getId()))
                    .collect(Collectors.toList());
            return list.stream()
                    .filter(e -> acquaitanceUsers.contains(e.getMatchMember().getUser()))
                    .map(EventResponse::new)
                    .collect(Collectors.toList());
        }
        return list.stream().map(EventResponse::new).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        eventDao.deleteById(id);
    }

    @Override
    public EventResponse findById(long id) {
        Optional<Event> optionalEvent = eventDao.findById(id);
        return optionalEvent.map(EventResponse::new).orElseThrow(() -> new NotFoundException("EventResponse"));
    }

    @Override
    public EventResponse update(EventDto eventDto, long id) {
        Optional<Event> optionalEvent = eventDao.findById(id);
        if (optionalEvent.isPresent()) {
            return saveOrUpdate(eventDto);
        } else throw new NotFoundException("Event");
    }

    @Override
    @Transactional
    public EventResponse save(EventDto eventDto) {
        return saveOrUpdate(eventDto);
    }

    @Transactional
    private EventResponse saveOrUpdate(EventDto eventDto) {
        FootballMatch footballMatch = null;
        if (eventDto.getFootballMatchId() != null) {
            final Optional<FootballMatch> existingFootballMatch = footballMatchDao.findById(eventDto.getFootballMatchId());
            if (existingFootballMatch.isPresent()) {
                footballMatch = existingFootballMatch.get();
            } else throw new NotFoundException("FootballMatch");
        }
        MatchMember matchMember = null;
        if (eventDto.getMatchMemberId() != null) {
            final Optional<MatchMember> existingMatchMember = matchMemberDao.findById(eventDto.getMatchMemberId());
            if (existingMatchMember.isPresent()) {
                matchMember = existingMatchMember.get();
            } else throw new NotFoundException("MathMember");
        }
        final Event event = Event.builder()
                .footballMatch(footballMatch)
                .matchMember(matchMember)
                .type(eventDto.getType())
                .dateTime(dateUtils.convertFromString(eventDto.getDateTime()))
                .build();
        if (eventDto.getId() != null) {
            event.setId(eventDto.getId());
        }
        Event savedEvent = eventDao.save(event);
        return new EventResponse(savedEvent);
    }

    @Override
    @Transactional
    public List<EventResponse> saveProtocol(MatchProtocolDto matchProtocolDto, long id) {
        final List<EventDto> dtoEvents = matchProtocolDto.getEvents().stream().peek(eventDto -> eventDto.setFootballMatchId(id)).collect(Collectors.toList());
        final List<Long> dtoEventsIds = dtoEvents.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<Event> eventsList = new ArrayList<>();
        eventDao.findAll().iterator().forEachRemaining(eventsList::add);
        final List<Long> eventsIds = eventsList.stream()
                .filter(e -> e.getFootballMatch().getId().equals(id))
                .map(e -> e.getId())
                .collect(Collectors.toList());
        eventsIds.removeAll(dtoEventsIds);
        for (Long eventId: eventsIds) {
            eventDao.deleteById(eventId);
        }
        final List<EventResponse> responseEvents = dtoEvents.stream().map(this::save).collect(Collectors.toList());
        final Optional<FootballMatch> footballMatchOptional = footballMatchDao.findById(id);
        if (footballMatchOptional.isPresent()) {
            FootballMatch newFootballMatch = footballMatchOptional.get();
            newFootballMatch.setHasProtocol(true);
            if (newFootballMatch.getTeamsInvolved() == null) {
                throw new NotFoundException("No teams found for match: " + newFootballMatch.getId());
            }
            if (newFootballMatch.getTeamsInvolved().size() < 2) {
                throw new NotFoundException("Less than 2 teams for match: " + newFootballMatch.getId());
            }
            final Team team1 = newFootballMatch.getTeamsInvolved().get(0);
            final Team team2 = newFootballMatch.getTeamsInvolved().get(1);
            final long team1Score = newFootballMatch
                    .getEventsInvolvingMatch()
                    .stream()
                    .filter(event -> event.getType().equals(Constants.EVENT_GOAL) && event.getMatchMember() != null && event.getMatchMember().getTeam().equals(team1))
                    .count();
            final long team2Score = newFootballMatch
                    .getEventsInvolvingMatch()
                    .stream()
                    .filter(event -> event.getType().equals(Constants.EVENT_GOAL) && event.getMatchMember() != null && event.getMatchMember().getTeam().equals(team2))
                    .count();
            newFootballMatch.setMatchScore(team1Score + " : " + team2Score);
            footballMatchDao.save(newFootballMatch);
        } else throw new NotFoundException("FootballMatch");
        return responseEvents;
    }

    @Override
    @Transactional
    public List<EventResponse> findProtocol(long id) {
        final Optional<FootballMatch> footballMatchOptional = footballMatchDao.findById(id);
        if (footballMatchOptional.isPresent()) {
            FootballMatch newFootballMatch = footballMatchOptional.get();
            footballMatchDao.save(newFootballMatch);
        } else throw new NotFoundException("FootballMatch");
        final List<Event> events = eventDao.findByFootballMatchId(id);
        return events.stream().map(EventResponse::new).collect(Collectors.toList());
    }
}
