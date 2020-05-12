package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.FootballPitchDto;
import pwr.zpi.socialballspring.dto.Response.FootballPitchResponse;

import java.util.List;

public interface FootballPitchService {
    FootballPitchResponse save(FootballPitchDto footballPitch);

    List<FootballPitchResponse> findAll();

    void delete(long id);

    FootballPitchResponse findById(long id);

    FootballPitchResponse update(FootballPitchDto footballpitch, long id);
}
