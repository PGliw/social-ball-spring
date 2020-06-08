package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.FavouritePositionDto;
import pwr.zpi.socialballspring.dto.Response.FavouritePositionResponse;

import java.util.List;

public interface FavouritePositionService {
    FavouritePositionResponse save(FavouritePositionDto favouritePosition);

    List<FavouritePositionResponse> findAll(long id);

    void delete(long id);

    FavouritePositionResponse findById(long id);

    FavouritePositionResponse update(FavouritePositionDto favouritePosition, long id);
}
