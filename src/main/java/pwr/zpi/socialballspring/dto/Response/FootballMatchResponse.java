package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.FootballMatch;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FootballMatchResponse {
    private Long id;
    private String title;
    private String beginningTime;
    private String endingTime;
    private String description;
    private UserResponse organizer;
    private FootballPitchResponse pitch;
    private List<TeamResponse> teams;
    private boolean ifFinished;

    public FootballMatchResponse(FootballMatch footballMatch) {
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
        this.description = footballMatch.getDescription();
        if (footballMatch.getOrganizer() != null) {
            this.organizer = new UserResponse(footballMatch.getOrganizer());
        }
        if (footballMatch.getFootballPitch() != null) {
            this.pitch = new FootballPitchResponse(footballMatch.getFootballPitch());
        }
        if (footballMatch.getMatchMembers() != null) {
            this.teams = footballMatch.getTeamsInvolved().stream().map(TeamResponse::new).collect(Collectors.toList());
        }
    }
}
