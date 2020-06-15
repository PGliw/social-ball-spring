package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.dto.Response.Details.FootballMatchResponseDetails;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.MatchMember;
import pwr.zpi.socialballspring.model.Team;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.util.Constants;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class FootballMatchResponse {
    private Long id;
    private String title;
    private String beginningTime;
    private String endingTime;
    private boolean isFinished;
    private boolean hasProtocol;
    private boolean isCurrentUserOrganizer;
    private Long currentUserMatchMemberId;
    private String currentUserPositionName;
    private String currentUserPositionSideName;
    private String score;
    private String statusTime;
    private FootballMatchResponseDetails details;
    private String teamNames;

    public FootballMatchResponse(FootballMatch footballMatch, User currentUser, boolean isDetailed) {
        this.id = footballMatch.getId();
        if (footballMatch.getTitle() != null) {
            title = footballMatch.getTitle();
        }
        if (footballMatch.getBeginningTime() != null) {
            this.beginningTime = footballMatch.getBeginningTime().toString();
        }
        if (footballMatch.getEndingTime() != null) {
            this.endingTime = footballMatch.getEndingTime().toString();
        }
        if (footballMatch.getIsFinished() != null) {
            this.isFinished = footballMatch.getIsFinished();
        }
        if (footballMatch.getHasProtocol() != null) {
            this.hasProtocol = footballMatch.getHasProtocol();
            this.score = footballMatch.getMatchScore();
        }
        if (footballMatch.getBeginningTime() != null && footballMatch.getEndingTime() != null) {
            if (LocalDateTime.now().isBefore(footballMatch.getBeginningTime())) {
                this.statusTime = Constants.TIME_FUTURE;
            } else if (LocalDateTime.now().isAfter(footballMatch.getEndingTime())) {
                this.statusTime = Constants.TIME_PAST;
            } else {
                this.statusTime = Constants.TIME_PRESENT;
            }
        }
        if (footballMatch.getOrganizer() != null) {
            isCurrentUserOrganizer = footballMatch.getOrganizer().getId().equals(currentUser.getId());
        }
        if (footballMatch.getTeamsInvolved() != null && footballMatch.getTeamsInvolved().size() == 2) {
            // current user position and side
            final long currentUserId = currentUser.getId();
            Optional<MatchMember> currentMatchMember = footballMatch
                    .getTeamsInvolved()
                    .stream()
                    .map(Team::getTeamMembers)
                    .flatMap(Collection::stream)
                    .filter(matchMember -> {
                        if (matchMember.getUser() == null) return false;
                        final long matchMemberUserId = matchMember.getUser().getId();
                        return matchMemberUserId == currentUserId;
                    })
                    .findFirst();

            currentMatchMember.ifPresent(matchMember -> {
                currentUserPositionName = matchMember.getPosition().getName();
                currentUserPositionSideName = matchMember.getPosition().getSide();
                currentUserMatchMemberId = matchMember.getId();
            });

            // team names
            teamNames = footballMatch.getTeamsInvolved().stream().map(Team::getName).collect(Collectors.joining(" - "));
        }
        if (isDetailed) {
            details = new FootballMatchResponseDetails(footballMatch);
        }
    }
}

