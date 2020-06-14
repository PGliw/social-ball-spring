package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.FootballMatchDto;
import pwr.zpi.socialballspring.dto.MatchProtocolDto;
import pwr.zpi.socialballspring.dto.Response.EventResponse;
import pwr.zpi.socialballspring.dto.Response.FootballMatchResponse;
import pwr.zpi.socialballspring.service.EventService;
import pwr.zpi.socialballspring.service.FootballMatchService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/footballMatches")
public class FootballMatchController {
    @Autowired
    private FootballMatchService footballMatchService;

    @Autowired
    private EventService eventService;

    @Autowired
    private IIdentityManager identityManager;

    @PostMapping
    public ResponseEntity<FootballMatchResponse> saveFootballMatch(@RequestBody FootballMatchDto footballMatch) {
        return new ResponseEntity<>(footballMatchService.save(footballMatch), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FootballMatchResponse>> listFootballMatch(
            @RequestParam Optional<Boolean> organizer,
            @RequestParam Optional<Boolean> player,
            @RequestParam Optional<Boolean> detailed
    ) {
        boolean isOrganizer = organizer.isPresent() && organizer.get();
        boolean isPlayer = player.isPresent() && player.get();
        boolean isResponseDetailed = detailed.isPresent() && detailed.get();
        return new ResponseEntity<>(footballMatchService.findAll(isOrganizer, isPlayer, isResponseDetailed), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FootballMatchResponse> getOne(@PathVariable long id) {
        return new ResponseEntity<>(footballMatchService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FootballMatchResponse> update(@RequestBody FootballMatchDto footballMatchDto, @PathVariable long id) {
        return new ResponseEntity<>(footballMatchService.update(footballMatchDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        footballMatchService.delete(id);
        return new ResponseEntity<>("FootballMatch deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<List<EventResponse>> getEventsByMatchId(@PathVariable long id) {
        return new ResponseEntity<>(eventService.findProtocol(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/events")
    public ResponseEntity<List<EventResponse>> saveMatchProtocol(@RequestBody MatchProtocolDto matchProtocolDto, @PathVariable long id) {
        return new ResponseEntity<>(eventService.saveProtocol(matchProtocolDto, id), HttpStatus.OK);
    }
}
