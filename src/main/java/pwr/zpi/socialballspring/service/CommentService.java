package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.CommentDto;
import pwr.zpi.socialballspring.dto.Response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse save(CommentDto comment);

    List<CommentResponse> findAll();

    void delete(long id);

    CommentResponse findById(long id);

    CommentResponse update(CommentDto comment, long id);
}
