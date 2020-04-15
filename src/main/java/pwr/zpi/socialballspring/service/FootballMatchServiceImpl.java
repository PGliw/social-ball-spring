package pwr.zpi.socialballspring.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.FootballMatchDto;
import pwr.zpi.socialballspring.dto.Response.FootballMatchResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.UserDao;
import pwr.zpi.socialballspring.util.dateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "footballMatchService")
public class FootballMatchServiceImpl implements FootballMatchService{

    @Autowired
    FootballMatchDao footballMatchDao;

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
            LocalDateTime endingTime = endingTime = dateUtils.convertFromString(footballMatchDto.getEndingTime());
            FootballMatch footballMatch = FootballMatch.builder()
                    .description(footballMatchDto.getDescription())
                    .beginningTime(beginningTime)
                    .endingTime(endingTime)
                    .id(id)
                    .build();
            FootballMatch savedFootballMatch = footballMatchDao.save(footballMatch);
            return new FootballMatchResponse(savedFootballMatch);
        } else throw new NotFoundException("FootballMatch");
    }

    @Override
    public FootballMatchResponse save(FootballMatchDto footballMatchDto) {
        LocalDateTime beginningTime = dateUtils.convertFromString(footballMatchDto.getBeginningTime());
        LocalDateTime endingTime = dateUtils.convertFromString(footballMatchDto.getEndingTime());
        FootballMatch newFootballMach = FootballMatch.builder()
                .description(footballMatchDto.getDescription())
                .beginningTime(beginningTime)
                .endingTime(endingTime)
                .build();

        Optional<User> optionalUser = userDao.findById(footballMatchDto.getId());
        if(optionalUser.isPresent()) {
            User organizer = optionalUser.get();
            newFootballMach.setOrganizer(organizer);
        }
        else{
            throw new NotFoundException("organizerId");
        }
        return new FootballMatchResponse(footballMatchDao.save(newFootballMach));
    }

}
