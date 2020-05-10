package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.EventDto;
import pwr.zpi.socialballspring.dto.Response.EventResponse;

import java.util.List;

public interface EventService {
    EventResponse save(EventDto event);

    List<EventResponse> findAll();

    void delete(long id);

    EventResponse findById(long id);

    EventResponse update(EventDto event, long id);
}
