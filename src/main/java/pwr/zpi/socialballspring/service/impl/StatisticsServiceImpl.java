package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.Response.StatisticsResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.Event;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.MatchMember;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.repository.EventDao;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.UserDao;
import pwr.zpi.socialballspring.service.StatisticsService;
import pwr.zpi.socialballspring.util.Constants;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        return events.stream().filter(e -> e.getType().equals(Constants.EVENT_GOAL)).count();
    }
}
