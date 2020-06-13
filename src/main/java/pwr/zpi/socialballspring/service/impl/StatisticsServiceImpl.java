package pwr.zpi.socialballspring.service.impl;

import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.Response.MonthStatisticsResponse;
import pwr.zpi.socialballspring.dto.Response.StatisticsResponse;
import pwr.zpi.socialballspring.dto.Response.TimeStatisticsResponse;
import pwr.zpi.socialballspring.model.Event;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.MatchMember;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.repository.EventDao;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.UserDao;
import pwr.zpi.socialballspring.service.StatisticsService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service(value = "statisticsService")
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    UserDao userDao;

    @Autowired
    EventDao eventDao;

    @Autowired
    FootballMatchDao footballMatchDao;

    @Autowired
    IIdentityManager identityManager;

    @Override
    public StatisticsResponse findById(){
        List<MatchMember> appearances = identityManager.getCurrentUser().getAppearancesAsMatchMember();
        long goals = 0;
        long yellowCards = 0;
        long redCards = 0;
        long fauls = 0;
        long matches = 0;
        if(appearances != null) {
            matches = appearances.stream().filter(a -> a.getFootballMatch().getIfFinished()).count();
        }
        long minutesPlayed = 0;
        if(appearances != null) {
            for (MatchMember appearance : appearances) {
                goals += appearance.getEventsInvolvingMatchMember().stream()
                        .filter(e -> e.getType().equals("Strzelenie gola")).count();
                yellowCards += appearance.getEventsInvolvingMatchMember().stream()
                        .filter(e -> e.getType().equals("Żółta kartka")).count();
                redCards += appearance.getEventsInvolvingMatchMember().stream()
                        .filter(e -> e.getType().equals("Czerwona kartka")).count();
                fauls += appearance.getEventsInvolvingMatchMember().stream()
                        .filter(e -> e.getType().equals("Faul")).count();
                if(appearance.getFootballMatch().getBeginningTime() != null && appearance.getFootballMatch().getEndingTime() != null && appearance.getFootballMatch().getIfFinished()) {
                    minutesPlayed = Duration.between(appearance.getFootballMatch().getBeginningTime(),
                            appearance.getFootballMatch().getEndingTime()).toMinutes();
                }
            }
        }
        long hoursPlayed = minutesPlayed/60;
        return new StatisticsResponse(goals, matches, hoursPlayed, yellowCards, redCards, fauls);
    }

    @Override
    public StatisticsResponse findByUser(long id){
        List<MatchMember> appearances = userDao.findById(id).get().getAppearancesAsMatchMember();
        long goals = 0;
        long yellowCards = 0;
        long redCards = 0;
        long fauls = 0;
        long matches = 0;
        if(appearances != null) {
            matches = appearances.stream().filter(a -> a.getFootballMatch().getIfFinished()).count();
        }
        long minutesPlayed = 0;
        if(appearances != null) {
            for (MatchMember appearance : appearances) {
                goals += appearance.getEventsInvolvingMatchMember().stream()
                        .filter(e -> e.getType().equals("Strzelenie gola")).count();
                yellowCards += appearance.getEventsInvolvingMatchMember().stream()
                        .filter(e -> e.getType().equals("Żółta kartka")).count();
                redCards += appearance.getEventsInvolvingMatchMember().stream()
                        .filter(e -> e.getType().equals("Czerwona kartka")).count();
                fauls += appearance.getEventsInvolvingMatchMember().stream()
                        .filter(e -> e.getType().equals("Faul")).count();
                if (appearance.getFootballMatch().getBeginningTime() != null && appearance.getFootballMatch().getEndingTime() != null && appearance.getFootballMatch().getIfFinished()) {
                    minutesPlayed = Duration.between(appearance.getFootballMatch().getBeginningTime(),
                            appearance.getFootballMatch().getEndingTime()).toMinutes();
                }
            }
        }
        long hoursPlayed = minutesPlayed/60;
        return new StatisticsResponse(goals, matches, hoursPlayed, yellowCards, redCards, fauls);
    }

    @Override
    public StatisticsResponse findGlobal(){
        List<Event> list = new ArrayList<>();
        eventDao.findAll().iterator().forEachRemaining(list::add);
        long goals = list.stream().filter(e -> e.getType().equals("Strzelenie gola")).count();
        long yellowCards = list.stream().filter(e -> e.getType().equals("Żółta kartka")).count();
        long redCards = list.stream().filter(e -> e.getType().equals("Czerwona kartka")).count();
        long fauls = list.stream().filter(e -> e.getType().equals("Faul")).count();
        List<FootballMatch> matchesList = new ArrayList<>();
        footballMatchDao.findAll().iterator().forEachRemaining(matchesList::add);
        long matches = matchesList.size();
        long hoursPlayed = 0;
        return new StatisticsResponse(goals, matches, hoursPlayed, yellowCards, redCards, fauls);
    }

    @Override
    public TimeStatisticsResponse findTimeStats(long monthsNumber){
        LocalDate localDate = LocalDate.now();
        List<FootballMatch> matchesList = new ArrayList<>();
        footballMatchDao.findAll().iterator().forEachRemaining(matchesList::add);
        List<User> users = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(users::add);
        List<MonthStatisticsResponse> monthStatisticsResponses = new ArrayList<>();
        for(int i = 0; i < monthsNumber; i++){
            LocalDate minusDate = localDate.minusMonths(i);
            Month month = minusDate.getMonth();
            long matches = matchesList.stream()
                    .filter(m -> Objects.nonNull(m.getBeginningTime()))
                    .filter(m -> m.getBeginningTime().getMonth().equals(month))
                    .count();
            long players = users.stream()
                    .map(m -> m.getAppearancesAsMatchMember().stream()
                            .filter(p -> Objects.nonNull(p.getFootballMatch()))
                            .filter(p -> Objects.nonNull(p.getFootballMatch().getBeginningTime()))
                            .filter(p -> p.getFootballMatch().getBeginningTime().getMonth().equals(month))
                    .collect(toList()))
                    .filter(p -> p.size() > 0)
                    .count();
            monthStatisticsResponses.add(new MonthStatisticsResponse(month.toString()+" " + minusDate.getYear(), matches, players));
        }
        return new TimeStatisticsResponse(monthStatisticsResponses);
    }
}
