package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.dto.TeamDto;
import pwr.zpi.socialballspring.dto.Response.TeamResponse;
import pwr.zpi.socialballspring.service.TeamService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponse> saveTeam(@RequestBody TeamDto Team) {
        return new ResponseEntity<>(teamService.save(Team), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TeamResponse>> listTeam() {
        return new ResponseEntity<>(teamService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getOne(@PathVariable long id) {
        return new ResponseEntity<>(teamService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse> update(@RequestBody TeamDto TeamDto, @PathVariable long id) {
        return new ResponseEntity<>(teamService.update(TeamDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        teamService.delete(id);
        return new ResponseEntity<>("Team deleted successfully", HttpStatus.OK);
    }
}
