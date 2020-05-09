package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.dto.FootballPitchDto;
import pwr.zpi.socialballspring.dto.Response.FootballPitchResponse;
import pwr.zpi.socialballspring.service.FootballPitchService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/footballPitch")
public class FootballPitchController {
    @Autowired
    private FootballPitchService footballPitchService;

    @PostMapping
    public ResponseEntity<FootballPitchResponse> saveFootballPitch(@RequestBody FootballPitchDto footballPitch) {
        return new ResponseEntity<>(footballPitchService.save(footballPitch), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FootballPitchResponse>> listFootballPitch() {
        return new ResponseEntity<>(footballPitchService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FootballPitchResponse> getOne(@PathVariable long id) {
        return new ResponseEntity<>(footballPitchService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FootballPitchResponse> update(@RequestBody FootballPitchDto footballPitchDto, @PathVariable long id) {
        return new ResponseEntity<>(footballPitchService.update(footballPitchDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        footballPitchService.delete(id);
        return new ResponseEntity<>("FootballPitch deleted successfully", HttpStatus.OK);
    }
}
