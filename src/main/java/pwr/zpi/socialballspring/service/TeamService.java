package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.TeamDto;
import pwr.zpi.socialballspring.dto.Response.TeamResponse;

import java.util.List;

public interface TeamService {
    TeamResponse save(TeamDto team);

    List<TeamResponse> findAll();

    void delete(long id);

    TeamResponse findById(long id);

    TeamResponse update(TeamDto team, long id);
}
