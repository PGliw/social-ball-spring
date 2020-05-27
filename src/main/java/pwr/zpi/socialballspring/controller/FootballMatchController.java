package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.FootballMatchDto;
import pwr.zpi.socialballspring.dto.Response.FootballMatchResponse;
import pwr.zpi.socialballspring.service.FootballMatchService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/footballMatches")
public class FootballMatchController {
    @Autowired
    private FootballMatchService footballMatchService;

    @Autowired
    private IIdentityManager identityManager;

    @PostMapping
    public ResponseEntity<FootballMatchResponse> saveFootballMatch(@RequestBody FootballMatchDto footballMatch) {
        return new ResponseEntity<>(footballMatchService.save(footballMatch), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FootballMatchResponse>> listFootballMatch() {
        return new ResponseEntity<>(footballMatchService.findAll(), HttpStatus.OK);
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
}
