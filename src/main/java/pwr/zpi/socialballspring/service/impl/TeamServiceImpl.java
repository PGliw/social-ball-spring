package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.Response.TeamResponse;
import pwr.zpi.socialballspring.dto.TeamDto;
import pwr.zpi.socialballspring.exception.BadRequestException;
import pwr.zpi.socialballspring.exception.ConflictException;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.MatchMember;
import pwr.zpi.socialballspring.model.Team;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.TeamDao;
import pwr.zpi.socialballspring.service.MatchMemberService;
import pwr.zpi.socialballspring.service.TeamService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "teamService")
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamDao teamDao;

    @Autowired
    FootballMatchDao footballMatchDao;

    @Autowired
    MatchMemberService matchMemberService;

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
        final Optional<Team> optionalTeam = teamDao.findById(id);
        if (optionalTeam.isPresent()) {
            FootballMatch footballMatch = null;
            if (teamDto.getFootballMatchId() != null) {
                footballMatch = footballMatchDao.findById(teamDto.getFootballMatchId()).orElseThrow(() -> new NotFoundException("FootballMatch"));
            }
            Team team = Team.builder()
                    .footballMatch(footballMatch)
                    .name(teamDto.getName())
                    .membersCount(teamDto.getMembersCount())
                    .shirtColours(teamDto.getShirtColours())
                    .build();
            Team savedTeam = teamDao.save(team);
            return new TeamResponse(savedTeam);
        } else throw new NotFoundException("Team");
    }

    @Override
    public TeamResponse save(TeamDto teamDto) {
        if (teamDto.getFootballMatchId() == null) throw new BadRequestException("Match ID cannot be null");

        final FootballMatch  footballMatch = footballMatchDao.findById(teamDto.getFootballMatchId()).orElseThrow(() -> new NotFoundException("FootballMatch"));

        if (footballMatch.getTeamsInvolved().size() >= 2) throw new ConflictException("There are already 2 teams assigned to the match");

        Team team = Team.builder()
                .footballMatch(footballMatch)
                .name(teamDto.getName())
                .membersCount(teamDto.getMembersCount())
                .shirtColours(teamDto.getShirtColours())
                .build();
        Team savedTeam = teamDao.save(team);

        if (teamDto.getTeamMembers() != null) {
            teamDto.getTeamMembers().forEach(teamMember -> {
                teamMember.setTeamId(savedTeam.getId());
                teamMember.setFootballMatchId(savedTeam.getFootballMatch().getId());
                matchMemberService.save(teamMember);
            });
        }
        Team teamWithMembers = teamDao.findById(savedTeam.getId()).orElseThrow(() -> new NotFoundException("FootballMatch"));
        return new TeamResponse(teamWithMembers);
    }

}
