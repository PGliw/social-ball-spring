package pwr.zpi.socialballspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.TeamDto;
import pwr.zpi.socialballspring.dto.Response.TeamResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.Team;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.TeamDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "teamService")
public class TeamServiceImpl implements TeamService{
    @Autowired
    TeamDao teamDao;

    @Autowired
    FootballMatchDao footballMatchDao;

    @Override
    public List<TeamResponse> findAll() {
        List<Team> list = new ArrayList<>();
        teamDao.findAll().iterator().forEachRemaining(list::add);
        return list.stream().map(TeamResponse::new).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        teamDao.deleteById(id);
    }

    @Override
    public TeamResponse findById(long id) {
        Optional<Team> optionalTeam = teamDao.findById(id);
        return optionalTeam.map(TeamResponse::new).orElseThrow(() -> new NotFoundException("TeamResponse"));
    }

    @Override
    public TeamResponse update(TeamDto teamDto, long id) {
        Optional<Team> optionalTeam = teamDao.findById(id);
        if (optionalTeam.isPresent()) {
            FootballMatch footballMatch = null;
            if(teamDto.getFootballMatchId() != null){
                footballMatch = footballMatchDao.findById(teamDto.getFootballMatchId()).get();
            }
            Team team = Team.builder()
                    .footballMatch(footballMatch)
                    .id(id)
                    .membersCount(teamDto.getMembersCount())
                    .shirtColours(teamDto.getShirtColours())
                    .build();
            Team savedTeam = teamDao.save(team);
            return new TeamResponse(savedTeam);
        } else throw new NotFoundException("Team");
    }

    @Override
    public TeamResponse save(TeamDto teamDto) {
        FootballMatch footballMatch = null;
        if (teamDto.getFootballMatchId() != null) {
            footballMatch = footballMatchDao.findById(teamDto.getFootballMatchId()).get();
        }
        Team team = Team.builder()
                .footballMatch(footballMatch)
                .membersCount(teamDto.getMembersCount())
                .shirtColours(teamDto.getShirtColours())
                .build();
        Team savedTeam = teamDao.save(team);
        return new TeamResponse(savedTeam);
    }
}
