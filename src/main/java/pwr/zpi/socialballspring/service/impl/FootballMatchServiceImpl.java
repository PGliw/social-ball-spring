package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.FootballMatchDto;
import pwr.zpi.socialballspring.dto.Response.FootballMatchResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.FootballPitch;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.FootballPitchDao;
import pwr.zpi.socialballspring.service.FootballMatchService;
import pwr.zpi.socialballspring.util.dateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "footballMatchService")
public class FootballMatchServiceImpl implements FootballMatchService {

    @Autowired
    FootballMatchDao footballMatchDao;

    @Autowired
    FootballPitchDao footballPitchDao;

    @Autowired
    IIdentityManager identityManager;

    @Override
    public List<FootballMatchResponse> findAll(Optional<Boolean> onlyMyMatches) {
        List<FootballMatch> list = new ArrayList<>();
        footballMatchDao.findAll().iterator().forEachRemaining(list::add);
        if(onlyMyMatches.isPresent() && onlyMyMatches.get().equals(true)) {
            return list.stream()
                    .filter(
                            m -> m.getMatchMembers().stream()
                                    .filter(mem -> mem.getUser().getId().equals(identityManager.getCurrentUser().getId()))
                            .count() > 0 ||
                            m.getOrganizer().getId().equals(identityManager.getCurrentUser().getId()))
            .map(f -> new FootballMatchResponse(f, f.getOrganizer().getId().equals(identityManager.getCurrentUser().getId()))).collect(Collectors.toList());
        } else {
            return list.stream().map(f -> new FootballMatchResponse(f, f.getOrganizer().getId().equals(identityManager.getCurrentUser().getId()))).collect(Collectors.toList());
        }
    }

    @Override
    public void delete(long id) {
        footballMatchDao.deleteById(id);
    }

    @Override
    public FootballMatchResponse findById(long id) {
        Optional<FootballMatch> optionalFootballMatch = footballMatchDao.findById(id);
        if(optionalFootballMatch.isPresent()){
            return new FootballMatchResponse(optionalFootballMatch.get(), optionalFootballMatch.get().getOrganizer().getId().equals(identityManager.getCurrentUser().getId()));
        } else {
            throw new NotFoundException("FootballMatchResponse");
        }
    }

    @Override
    public FootballMatchResponse update(FootballMatchDto footballMatchDto, long id) {
        Optional<FootballMatch> optionalFootballMatch = footballMatchDao.findById(id);
        if (optionalFootballMatch.isPresent()) {
            LocalDateTime beginningTime = dateUtils.convertFromString(footballMatchDto.getBeginningTime());
            LocalDateTime endingTime = dateUtils.convertFromString(footballMatchDto.getEndingTime());
            FootballPitch footballPitch = null;
            if (footballMatchDto.getPitchId() != null) {
                footballPitch = footballPitchDao.findById(footballMatchDto.getPitchId()).get();
            }
            FootballMatch footballMatch = FootballMatch.builder()
                    .title(footballMatchDto.getTitle())
                    .description(footballMatchDto.getDescription())
                    .beginningTime(beginningTime)
                    .endingTime(endingTime)
                    .ifFinished(footballMatchDto.getIfFinished())
                    .id(id)
                    .matchScore("0-0")
                    .organizer(identityManager.getCurrentUser())
                    .footballPitch(footballPitch)
                    .build();
            FootballMatch savedFootballMatch = footballMatchDao.save(footballMatch);
            return new FootballMatchResponse(savedFootballMatch, savedFootballMatch.getOrganizer().getId().equals(identityManager.getCurrentUser().getId()));
        } else throw new NotFoundException("FootballMatch");
    }

    @Override
    public FootballMatchResponse save(FootballMatchDto footballMatchDto) {
        LocalDateTime beginningTime = dateUtils.convertFromString(footballMatchDto.getBeginningTime());
        LocalDateTime endingTime = dateUtils.convertFromString(footballMatchDto.getEndingTime());
        FootballPitch footballPitch = null;
        if (footballMatchDto.getPitchId() != null) {
            footballPitch = footballPitchDao.findById(footballMatchDto.getPitchId()).get();
        }
        FootballMatch newFootballMatch = FootballMatch.builder()
                .title(footballMatchDto.getTitle())
                .description(footballMatchDto.getDescription())
                .beginningTime(beginningTime)
                .endingTime(endingTime)
                .ifFinished(false)
                .matchScore("0-0")
                .organizer(identityManager.getCurrentUser())
                .footballPitch(footballPitch)
                .build();
        return new FootballMatchResponse(footballMatchDao.save(newFootballMatch), newFootballMatch.getOrganizer().getId().equals(identityManager.getCurrentUser().getId()));
    }

}
