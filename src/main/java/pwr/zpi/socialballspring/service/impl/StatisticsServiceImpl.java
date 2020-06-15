package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.Response.*;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.*;
import pwr.zpi.socialballspring.repository.EventDao;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.FootballPitchDao;
import pwr.zpi.socialballspring.repository.UserDao;
import pwr.zpi.socialballspring.service.StatisticsService;
import pwr.zpi.socialballspring.util.Constants;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    FootballPitchDao footballPitchDao;

    @Override
    public StatisticsResponse findByCurrentUser() {
        User user = identityManager.getCurrentUser();
        return getStatisticsResponseFor(user);
    }

    @Override
    public StatisticsResponse findById(Long id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("User");
        }
        return getStatisticsResponseFor(optionalUser.get());
    }

    @Override
    public StatisticsResponse findGlobal() {
        List<Event> list = new ArrayList<>();
        eventDao.findAll().iterator().forEachRemaining(list::add);
        long goals = countEventsBy(Constants.EVENT_GOAL, list);
        long yellowCards = countEventsBy(Constants.EVENT_YELLOW_CARD, list);
        long redCards = countEventsBy(Constants.EVENT_RED_CARD, list);
        long fauls = countEventsBy(Constants.EVENT_FOUL, list);
        List<FootballMatch> matchesList = new ArrayList<>();
        footballMatchDao.findAll().iterator().forEachRemaining(matchesList::add);
        long matches = matchesList.size();
        long hoursPlayed = 0;
        return new StatisticsResponse(goals, matches, hoursPlayed, yellowCards, redCards, fauls);
    }

    private StatisticsResponse getStatisticsResponseFor(User user) {
        List<MatchMember> appearances = user.getAppearancesAsMatchMember();
        long goals = 0;
        long yellowCards = 0;
        long redCards = 0;
        long fauls = 0;
        long matches = 0;
        if (appearances != null) {
            matches = appearances.stream().filter(a -> a.getFootballMatch().getIsFinished()).count();
        }
        long minutesPlayed = 0;
        if (appearances != null) {
            for (MatchMember appearance : appearances) {
                final List<Event> events = appearance.getEventsInvolvingMatchMember();
                goals += countEventsBy(Constants.EVENT_GOAL, events);
                yellowCards += countEventsBy(Constants.EVENT_YELLOW_CARD, events);
                redCards += countEventsBy(Constants.EVENT_RED_CARD, events);
                fauls += countEventsBy(Constants.EVENT_FOUL, events);
                if (appearance.getFootballMatch().getBeginningTime() != null && appearance.getFootballMatch().getEndingTime() != null && appearance.getFootballMatch().getIsFinished()) {
                    minutesPlayed = Duration.between(appearance.getFootballMatch().getBeginningTime(),
                            appearance.getFootballMatch().getEndingTime()).toMinutes();
                }
            }
        }
        long hoursPlayed = minutesPlayed / 60;
        return new StatisticsResponse(goals, matches, hoursPlayed, yellowCards, redCards, fauls);
    }


    private Long countEventsBy(String eventType, List<Event> events) {
        final List<String> allowedEventsTypes = Arrays.asList(Constants.EVENT_ASSIST, Constants.EVENT_FOUL, Constants.EVENT_GOAL, Constants.EVENT_INJURY, Constants.EVENT_RED_CARD, Constants.EVENT_YELLOW_CARD);
        if (!allowedEventsTypes.contains(eventType)) {
            throw new IllegalArgumentException("Illegal value of eventType: " + eventType);
        }
        return events.stream()
                .filter(e -> Objects.nonNull(e.getType()))
                .filter(e -> e.getType().equals(Constants.EVENT_GOAL)).count();
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

    @Override
    public FootballPitchStatsResponse findPitchStats(){
        List<FootballPitch> pitchesList = new ArrayList<>();
        footballPitchDao.findAll().iterator().forEachRemaining(pitchesList::add);
        List<FootballMatch> matchesList = new ArrayList<>();
        footballMatchDao.findAll().iterator().forEachRemaining(matchesList::add);
        List<FootbalPitchUnitStatsResponse> responses = new ArrayList<>();
        for (FootballPitch pitch: pitchesList){
            long pitchNum = matchesList.stream()
                    .filter(m -> Objects.nonNull(m.getFootballPitch()))
                    .filter(m -> m.getFootballPitch().getId().equals(pitch.getId()))
                    .count();
            long matchesNum = matchesList.stream().filter(m -> Objects.nonNull(m.getFootballPitch())).count();
            double percentage = 0;
            if(matchesNum > 0){
                percentage = (double)pitchNum/matchesNum*100;
            }
            responses.add(new FootbalPitchUnitStatsResponse(new FootballPitchResponse(pitch), percentage));
        }
        return new FootballPitchStatsResponse(responses.stream().sorted(Comparator.comparingDouble(FootbalPitchUnitStatsResponse::getPercentage).reversed()).collect(Collectors.toList()));
    }
}
