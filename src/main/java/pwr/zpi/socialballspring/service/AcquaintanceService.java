package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.AcquaitanceDto;
import pwr.zpi.socialballspring.dto.Response.AcquaitanceResponse;

import java.util.List;

public interface AcquaintanceService {
    AcquaitanceResponse save(AcquaitanceDto acquaintance);

    List<AcquaitanceResponse> findAll(long id);

    void delete(long id);

    AcquaitanceResponse findById(long id);

    AcquaitanceResponse update(AcquaitanceDto acquaintance, long id);
}
