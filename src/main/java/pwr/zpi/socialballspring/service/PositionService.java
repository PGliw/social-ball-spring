package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.PositionDto;
import pwr.zpi.socialballspring.dto.Response.PositionResponse;

import java.util.List;

public interface PositionService {
    PositionResponse save(PositionDto position);

    List<PositionResponse> findAll();

    void delete(long id);

    PositionResponse findById(long id);

    PositionResponse update(PositionDto position, long id);
}
