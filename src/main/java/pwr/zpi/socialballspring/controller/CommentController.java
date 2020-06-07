package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.dto.CommentDto;
import pwr.zpi.socialballspring.dto.Response.CommentResponse;
import pwr.zpi.socialballspring.service.CommentService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> saveComment(@RequestBody CommentDto Comment) {
        return new ResponseEntity<>(commentService.save(Comment), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> listComment(@RequestParam Optional<Long> matchId, @RequestParam Optional<Long> userId) {
        return new ResponseEntity<>(commentService.findAll(matchId, userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getOne(@PathVariable long id) {
        return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@RequestBody CommentDto CommentDto, @PathVariable long id) {
        return new ResponseEntity<>(commentService.update(CommentDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        commentService.delete(id);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
