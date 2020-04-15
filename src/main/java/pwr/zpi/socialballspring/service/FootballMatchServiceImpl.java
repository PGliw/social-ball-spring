package pwr.zpi.socialballspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.FootballMatchDto;
import pwr.zpi.socialballspring.dto.Response.FootballMatchResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.UserDao;
import pwr.zpi.socialballspring.util.dateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "footballMatchService")
public class FootballMatchServiceImpl implements FootballMatchService{

    @Autowired
    FootballMatchDao footballMatchDao;

    @Autowired
    IIdentityManager identityManager;

    @Autowired
    UserDao userDao;

    public List<FootballMatchResponse> findAll() {
        List<FootballMatch> list = new ArrayList<>();
        footballMatchDao.findAll().iterator().forEachRemaining(list::add);
        return list.stream().map(FootballMatchResponse::new).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        footballMatchDao.deleteById(id);
    }

    @Override
    public FootballMatchResponse findById(long id) {
        Optional<FootballMatch> optionalFootballMatch = footballMatchDao.findById(id);
        return optionalFootballMatch.map(FootballMatchResponse::new).orElseThrow(() -> new NotFoundException("FootballMatchResponse"));
    }

    @Override
    public FootballMatchResponse update(FootballMatchDto footballMatchDto, long id) {
        Optional<FootballMatch> optionalFootballMatch = footballMatchDao.findById(id);
        if (optionalFootballMatch.isPresent()) {
            LocalDateTime beginningTime = dateUtils.convertFromString(footballMatchDto.getBeginningTime());
            LocalDateTime endingTime = dateUtils.convertFromString(footballMatchDto.getEndingTime());
            FootballMatch footballMatch = FootballMatch.builder()
                    .description(footballMatchDto.getDescription())
                    .beginningTime(beginningTime)
                    .endingTime(endingTime)
                    .id(id)
                    .organizer(identityManager.getCurrentUser())
                    .build();
            FootballMatch savedFootballMatch = footballMatchDao.save(footballMatch);
            return new FootballMatchResponse(savedFootballMatch);
        } else throw new NotFoundException("FootballMatch");
    }

    @Override
    public FootballMatchResponse save(FootballMatchDto footballMatchDto) {
        LocalDateTime beginningTime = dateUtils.convertFromString(footballMatchDto.getBeginningTime());
        LocalDateTime endingTime = dateUtils.convertFromString(footballMatchDto.getEndingTime());
        FootballMatch newFootballMatch = FootballMatch.builder()
                .description(footballMatchDto.getDescription())
                .beginningTime(beginningTime)
                .endingTime(endingTime)
                .organizer(identityManager.getCurrentUser())
                .build();
        return new FootballMatchResponse(footballMatchDao.save(newFootballMatch));
    }

}
