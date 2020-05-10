package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.AcquaitanceDto;
import pwr.zpi.socialballspring.dto.Response.AcquaitanceResponse;

import java.util.List;

public interface AcquaitanceService {
    AcquaitanceResponse save(AcquaitanceDto acquaitance);

    List<AcquaitanceResponse> findAll();

    void delete(long id);

    AcquaitanceResponse findById(long id);

    AcquaitanceResponse update(AcquaitanceDto acquaitance, long id);
}
