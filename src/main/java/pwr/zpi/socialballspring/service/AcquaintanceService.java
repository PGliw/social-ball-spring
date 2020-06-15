package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.AcquaitanceDto;
import pwr.zpi.socialballspring.dto.Response.AcquaitanceResponse;
import pwr.zpi.socialballspring.dto.Response.UserAcquaitanceResponse;

import java.util.List;

public interface AcquaintanceService {
    AcquaitanceResponse save(AcquaitanceDto acquaintance);

    List<AcquaitanceResponse> findAll(long id, String status);

    void delete(long id);

    AcquaitanceResponse findById(long id);

    AcquaitanceResponse update(AcquaitanceDto acquaintance, long id);

    void send(long id);

    void accept(long id);

    void reject(long id);

    UserAcquaitanceResponse isAcquitanceSent(long id);
}
