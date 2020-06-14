package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.dto.EventDto;
import pwr.zpi.socialballspring.dto.Response.EventResponse;
import pwr.zpi.socialballspring.service.EventService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponse> saveEvent(@RequestBody EventDto Event) {
        return new ResponseEntity<>(eventService.save(Event), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> listEvent(@RequestParam Optional<Boolean> forAcquaitance) {
        return new ResponseEntity<>(eventService.findAll(forAcquaitance), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getOne(@PathVariable long id) {
        return new ResponseEntity<>(eventService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> update(@RequestBody EventDto EventDto, @PathVariable long id) {
        return new ResponseEntity<>(eventService.update(EventDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        eventService.delete(id);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
    }
}
