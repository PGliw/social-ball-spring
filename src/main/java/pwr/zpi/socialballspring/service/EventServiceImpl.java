package pwr.zpi.socialballspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.EventDto;
import pwr.zpi.socialballspring.dto.Response.EventResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.Event;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.MatchMember;
import pwr.zpi.socialballspring.repository.EventDao;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.MatchMemberDao;

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
            if(eventDto.getFootballMatchId() != null){
                footballMatch = footballMatchDao.findById(eventDto.getFootballMatchId()).get();
            }
            MatchMember matchMember = null;
            if(eventDto.getMatchMemberId() != null) {
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
    public EventResponse save(EventDto eventDto) {
        FootballMatch footballMatch = null;
        if(eventDto.getFootballMatchId() != null){
            footballMatch = footballMatchDao.findById(eventDto.getFootballMatchId()).get();
        }
        MatchMember matchMember = null;
        if(eventDto.getMatchMemberId() != null) {
            matchMember = matchMemberDao.findById(eventDto.getMatchMemberId()).get();
        }
        Event event = Event.builder()
                .footballMatch(footballMatch)
                .matchMember(matchMember)
                .type(eventDto.getType())
                .build();
        Event savedEvent = eventDao.save(event);
        return new EventResponse(savedEvent);
    }
}
