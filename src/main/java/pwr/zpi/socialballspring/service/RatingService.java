package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.RatingDto;
import pwr.zpi.socialballspring.dto.Response.RatingResponse;

import java.util.List;

public interface RatingService {
    RatingResponse save(RatingDto rating);

    List<RatingResponse> findAll();

    void delete(long id);

    RatingResponse findById(long id);

    RatingResponse update(RatingDto rating, long id);
}
