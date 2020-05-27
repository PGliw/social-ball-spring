package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.MatchMemberDto;
import pwr.zpi.socialballspring.dto.Response.MatchMemberResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.*;
import pwr.zpi.socialballspring.repository.*;
import pwr.zpi.socialballspring.service.MatchMemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "matchMemberService")
public class MatchMemeberServiceImpl implements MatchMemberService {
    @Autowired
    MatchMemberDao matchMemberDao;

    @Autowired
    TeamDao teamDao;

    @Autowired
    PositionDao positionDao;

    @Autowired
    UserDao userDao;

    @Autowired
    FootballMatchDao footballMatchDao;

    @Override
    public List<MatchMemberResponse> findAll() {
        List<MatchMember> list = new ArrayList<>();
        matchMemberDao.findAll().iterator().forEachRemaining(list::add);
        return list.stream().map(MatchMemberResponse::new).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        matchMemberDao.deleteById(id);
    }

    @Override
    public MatchMemberResponse findById(long id) {
        Optional<MatchMember> optionalMatchMember = matchMemberDao.findById(id);
        return optionalMatchMember.map(MatchMemberResponse::new).orElseThrow(() -> new NotFoundException("MatchMemberResponse"));
    }

    @Override
    public MatchMemberResponse update(MatchMemberDto matchMemberDto, long id) {
        Optional<MatchMember> optionalMatchMember = matchMemberDao.findById(id);
        if (optionalMatchMember.isPresent()) {
            User user = null;
            if(matchMemberDto.getUserId() != null){
                user = userDao.findById(matchMemberDto.getUserId()).get();
            }
            Team team = null;
            if(matchMemberDto.getTeamId() != null) {
                team = teamDao.findById(matchMemberDto.getTeamId()).get();
            }
            Position position = null;
            if(matchMemberDto.getPositionId() != null){
                position = positionDao.findById(matchMemberDto.getPositionId()).get();
            }
            FootballMatch footballMatch = null;
            if (matchMemberDto.getFootballMatchId() != null) {
                footballMatch = footballMatchDao.findById(matchMemberDto.getFootballMatchId()).orElseThrow(() -> new NotFoundException("Football match"));
            }

            MatchMember matchMember = MatchMember.builder()
                    .position(position)
                    .footballMatch(footballMatch)
                    .team(team)
                    .user(user)
                    .id(id)
                    .isConfirmed(matchMemberDto.isConfirmed())
                    .build();
            MatchMember savedMatchMember = matchMemberDao.save(matchMember);
            return new MatchMemberResponse(savedMatchMember);
        } else throw new NotFoundException("MatchMember");
    }

    @Override
    public MatchMemberResponse save(MatchMemberDto matchMemberDto) {
        User user = null;
        if(matchMemberDto.getUserId() != null){
            user = userDao.findById(matchMemberDto.getUserId()).get();
        }
        Team team = null;
        if(matchMemberDto.getTeamId() != null) {
            team = teamDao.findById(matchMemberDto.getTeamId()).get();
        }
        Position position = null;
        if(matchMemberDto.getPositionId() != null){
            position = positionDao.findById(matchMemberDto.getPositionId()).get();
        }
        FootballMatch footballMatch = null;
        if (matchMemberDto.getFootballMatchId() != null) {
            footballMatch = footballMatchDao.findById(matchMemberDto.getFootballMatchId()).orElseThrow(() -> new NotFoundException("Football match"));
        }
        MatchMember matchMember = MatchMember.builder()
                .position(position)
                .team(team)
                .footballMatch(footballMatch)
                .user(user)
                .isConfirmed(matchMemberDto.isConfirmed())
                .build();
        MatchMember savedMatchMember = matchMemberDao.save(matchMember);
        return new MatchMemberResponse(savedMatchMember);
    }
}
