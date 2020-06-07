package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.CommentDto;
import pwr.zpi.socialballspring.dto.Response.CommentResponse;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentResponse save(CommentDto comment);

    List<CommentResponse> findAll(Optional<Long> matchId, Optional<Long> userId);

    void delete(long id);

    CommentResponse findById(long id);

    CommentResponse update(CommentDto comment, long id);
}
