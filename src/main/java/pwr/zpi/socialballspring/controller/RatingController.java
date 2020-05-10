package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.dto.RatingDto;
import pwr.zpi.socialballspring.dto.Response.RatingResponse;
import pwr.zpi.socialballspring.service.RatingService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingResponse> saveRating(@RequestBody RatingDto Rating) {
        return new ResponseEntity<>(ratingService.save(Rating), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RatingResponse>> listRating() {
        return new ResponseEntity<>(ratingService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getOne(@PathVariable long id) {
        return new ResponseEntity<>(ratingService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponse> update(@RequestBody RatingDto RatingDto, @PathVariable long id) {
        return new ResponseEntity<>(ratingService.update(RatingDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        ratingService.delete(id);
        return new ResponseEntity<>("Rating deleted successfully", HttpStatus.OK);
    }
}
