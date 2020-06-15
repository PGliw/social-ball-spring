package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.dto.MatchMemberDto;
import pwr.zpi.socialballspring.dto.Response.MatchMemberResponse;
import pwr.zpi.socialballspring.service.MatchMemberService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/matchMembers")
public class MatchMemberController {
    @Autowired
    private MatchMemberService matchMemberService;

    @PostMapping
    public ResponseEntity<MatchMemberResponse> saveMatchMember(@RequestBody MatchMemberDto MatchMember) {
        return new ResponseEntity<>(matchMemberService.save(MatchMember), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MatchMemberResponse>> listMatchMember() {
        return new ResponseEntity<>(matchMemberService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchMemberResponse> getOne(@PathVariable long id) {
        return new ResponseEntity<>(matchMemberService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchMemberResponse> update(@RequestBody MatchMemberDto MatchMemberDto, @PathVariable long id) {
        return new ResponseEntity<>(matchMemberService.update(MatchMemberDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        matchMemberService.delete(id);
        return new ResponseEntity<>("MatchMember deleted successfully", HttpStatus.OK);
    }
}
