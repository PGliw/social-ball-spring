package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.Response.StatisticsResponse;
import pwr.zpi.socialballspring.model.MatchMember;
import pwr.zpi.socialballspring.repository.UserDao;
import pwr.zpi.socialballspring.service.StatisticsService;

import java.time.Duration;
import java.util.List;

@Service(value = "statisticsService")
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    UserDao userDao;

    @Autowired
    IIdentityManager identityManager;

    @Override
    public StatisticsResponse findById(){
        List<MatchMember> appearances = identityManager.getCurrentUser().getAppearancesAsMatchMember();
        long goals = 0;
        long matches = 0;
        if(appearances != null) {
            matches = appearances.stream().filter(a -> a.getFootballMatch().getIfFinished()).count();
        }
        long minutesPlayed = 0;
        if(appearances != null) {
            for (MatchMember appearance : appearances) {
                goals += appearance.getEventsInvolvingMatchMember().stream()
                        .filter(e -> e.getType().equals("Strzelenie gola")).count();
                if(appearance.getFootballMatch().getBeginningTime() != null && appearance.getFootballMatch().getEndingTime() != null && appearance.getFootballMatch().getIfFinished()) {
                    minutesPlayed = Duration.between(appearance.getFootballMatch().getBeginningTime(),
                            appearance.getFootballMatch().getEndingTime()).toMinutes();
                }
            }
        }
        long hoursPlayed = minutesPlayed/60;
        return new StatisticsResponse(goals, matches, hoursPlayed);
    }
}
