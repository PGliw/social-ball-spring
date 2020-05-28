package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.dto.PositionDto;
import pwr.zpi.socialballspring.dto.Response.PositionResponse;
import pwr.zpi.socialballspring.dto.Response.UserResponse;
import pwr.zpi.socialballspring.dto.UserDto;
import pwr.zpi.socialballspring.service.PositionService;
import pwr.zpi.socialballspring.service.UserService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/positions")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @PostMapping
    public ResponseEntity<PositionResponse> savePosition(@RequestBody PositionDto position) {
        return new ResponseEntity<>(positionService.save(position), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PositionResponse>> listPosition() {
        return new ResponseEntity<>(positionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponse> getPosition(@PathVariable long id) {
        return new ResponseEntity<>(positionService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionResponse> update(@RequestBody PositionDto positionDto, @PathVariable long id) {
        return new ResponseEntity<>(positionService.update(positionDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        positionService.delete(id);
        return new ResponseEntity<>("Position deleted successfully", HttpStatus.OK);
    }
}
