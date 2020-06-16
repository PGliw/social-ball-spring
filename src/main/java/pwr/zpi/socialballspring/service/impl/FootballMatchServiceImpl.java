package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.FootballMatchDto;
import pwr.zpi.socialballspring.dto.Response.FootballMatchResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.FootballPitch;
import pwr.zpi.socialballspring.model.MatchMember;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.FootballPitchDao;
import pwr.zpi.socialballspring.service.FootballMatchService;
import pwr.zpi.socialballspring.util.FunctionUtils;
import pwr.zpi.socialballspring.util.dateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service(value = "footballMatchService")
public class FootballMatchServiceImpl implements FootballMatchService {

    @Autowired
    FootballMatchDao footballMatchDao;

    @Autowired
    FootballPitchDao footballPitchDao;

    @Autowired
    IIdentityManager identityManager;

    @Override
    public List<FootballMatchResponse> findAll(boolean isOrganizer, boolean isPlayer, boolean isDetailed) {
        final User currentUser = identityManager.getCurrentUser();

        if (!isOrganizer && !isPlayer) {
            return StreamSupport.stream(footballMatchDao.findAll().spliterator(), false)
                    .map(footballMatch -> new FootballMatchResponse(footballMatch, currentUser, isDetailed))
                    .collect(Collectors.toList());
        }

        final Stream<FootballMatchResponse> matchesAsOrganizer = currentUser
                .getAppearancesAsMatchOrganizer()
                .stream()
                .filter(Objects::nonNull)
                .map(footballMatch -> new FootballMatchResponse(footballMatch, currentUser, isDetailed));

        final Stream<FootballMatchResponse> matchesAsParticipant = currentUser
                .getAppearancesAsMatchMember()
                .stream()
                .map(MatchMember::getFootballMatch)
                .filter(Objects::nonNull)
                .map(footballMatch -> new FootballMatchResponse(footballMatch, currentUser, isDetailed));

        if (isOrganizer && isPlayer) {
            return Stream.concat(matchesAsOrganizer, matchesAsParticipant)
                    .filter(FunctionUtils.distinctByKey(FootballMatchResponse::getId))
                    .collect(Collectors.toList());
        } else if (isOrganizer) {
            return matchesAsOrganizer.collect(Collectors.toList());

        } else {
            return matchesAsParticipant.collect(Collectors.toList());
        }
    }

    @Override
    public void delete(long id) {
        footballMatchDao.deleteById(id);
    }

    @Override
    public FootballMatchResponse findById(long id) {
        final User currentUser = identityManager.getCurrentUser();
        Optional<FootballMatch> optionalFootballMatch = footballMatchDao.findById(id);
        if (optionalFootballMatch.isPresent()) {
            return new FootballMatchResponse(optionalFootballMatch.get(), currentUser, true);
        } else {
            throw new NotFoundException("FootballMatch");
        }
    }

    @Override
    public FootballMatchResponse update(FootballMatchDto footballMatchDto, long id) {
        Optional<FootballMatch> optionalFootballMatch = footballMatchDao.findById(id);
        if (optionalFootballMatch.isPresent()) {
            footballMatchDto.setId(id);
            return saveFootballMatchFromDto(footballMatchDto);
        } else {
            throw new NotFoundException("FootballMatch");
        }
    }

    @Override
    public FootballMatchResponse save(FootballMatchDto footballMatchDto) {
        return saveFootballMatchFromDto(footballMatchDto);
    }

    private FootballMatchResponse saveFootballMatchFromDto(FootballMatchDto footballMatchDto) {
        final User currentUser = identityManager.getCurrentUser();
        LocalDateTime beginningTime = dateUtils.convertFromString(footballMatchDto.getBeginningTime());
        LocalDateTime endingTime = dateUtils.convertFromString(footballMatchDto.getEndingTime());
        FootballPitch footballPitch = null;
        if (footballMatchDto.getPitchId() != null) {
            Optional<FootballPitch> optionalFootballPitch = footballPitchDao.findById(footballMatchDto.getPitchId());
            if (optionalFootballPitch.isPresent()) {
                footballPitch = optionalFootballPitch.get();
            } else {
                throw new NotFoundException("FootballPitch");
            }
        }
        FootballMatch newFootballMatch = FootballMatch.builder()
                .title(footballMatchDto.getTitle())
                .description(footballMatchDto.getDescription())
                .beginningTime(beginningTime)
                .endingTime(endingTime)
                .isFinished(false)
                .matchScore(null)
                .organizer(identityManager.getCurrentUser())
                .footballPitch(footballPitch)
                .build();
        FootballMatch savedFootballMatch = footballMatchDao.save(newFootballMatch);
        return new FootballMatchResponse(savedFootballMatch, currentUser, true);
    }

    ;
}
