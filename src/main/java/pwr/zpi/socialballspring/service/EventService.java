package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.EventDto;
import pwr.zpi.socialballspring.dto.MatchProtocolDto;
import pwr.zpi.socialballspring.dto.Response.EventResponse;

import java.util.List;
import java.util.Optional;

public interface EventService {
    EventResponse save(EventDto event);

    List<EventResponse> findAll(Optional<Boolean> forAcquaitance);

    void delete(long id);

    EventResponse findById(long id);

    EventResponse update(EventDto event, long id);

    List<EventResponse> saveProtocol(MatchProtocolDto matchProtocolDto, long id);

    List<EventResponse> findProtocol(long id);
}
