package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.EventDto;
import pwr.zpi.socialballspring.dto.MatchProtocolDto;
import pwr.zpi.socialballspring.dto.Response.EventResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.Event;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.MatchMember;
import pwr.zpi.socialballspring.repository.EventDao;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.MatchMemberDao;
import pwr.zpi.socialballspring.service.EventService;
import pwr.zpi.socialballspring.util.dateUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "eventService")
public class EventServiceImpl implements EventService {
    @Autowired
    EventDao eventDao;

    @Autowired
    MatchMemberDao matchMemberDao;

    @Autowired
    FootballMatchDao footballMatchDao;

    @Override
    public List<EventResponse> findAll() {
        List<Event> list = new ArrayList<>();
        eventDao.findAll().iterator().forEachRemaining(list::add);
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
            FootballMatch footballMatch = null;
            if (eventDto.getFootballMatchId() != null) {
                footballMatch = footballMatchDao.findById(eventDto.getFootballMatchId()).get();
            }
            MatchMember matchMember = null;
            if (eventDto.getMatchMemberId() != null) {
                matchMember = matchMemberDao.findById(eventDto.getMatchMemberId()).get();
            }
            Event event = Event.builder()
                    .footballMatch(footballMatch)
                    .matchMember(matchMember)
                    .id(id)
                    .type(eventDto.getType())
                    .build();
            Event savedEvent = eventDao.save(event);
            return new EventResponse(savedEvent);
        } else throw new NotFoundException("Event");
    }

    @Override
    @Transactional
    public EventResponse save(EventDto eventDto) {
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
        Event savedEvent = eventDao.save(event);
        return new EventResponse(savedEvent);
    }

    @Override
    @Transactional
    public List<EventResponse> saveProtocol(MatchProtocolDto matchProtocolDto, long id) {
        final List<EventDto> dtoEvents = matchProtocolDto.getEvents().stream().peek(eventDto -> eventDto.setFootballMatchId(id)).collect(Collectors.toList());
        final List<EventResponse> responseEvents = dtoEvents.stream().map(this::save).collect(Collectors.toList());
        final Optional<FootballMatch> footballMatchOptional = footballMatchDao.findById(id);
        if (footballMatchOptional.isPresent()) {
            FootballMatch newFootballMatch = footballMatchOptional.get();
            newFootballMatch.setHasProtocol(true);
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
