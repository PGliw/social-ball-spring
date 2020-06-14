package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.FootballMatchDto;
import pwr.zpi.socialballspring.dto.Response.FootballMatchResponse;

import java.util.List;
import java.util.Optional;

public interface FootballMatchService {
    FootballMatchResponse save(FootballMatchDto footballMatch);

    List<FootballMatchResponse> findAll(boolean isOrganizer, boolean isPlayer, boolean isDetailed);

    void delete(long id);

    FootballMatchResponse findById(long id);

    FootballMatchResponse update(FootballMatchDto footballMatch, long id);
}
