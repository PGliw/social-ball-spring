package pwr.zpi.socialballspring.dto.Response.Details;

import lombok.Data;
import pwr.zpi.socialballspring.dto.Response.FootballPitchResponse;
import pwr.zpi.socialballspring.dto.Response.TeamResponse;
import pwr.zpi.socialballspring.dto.Response.UserResponse;
import pwr.zpi.socialballspring.model.FootballMatch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FootballMatchResponseDetails {
    private String description;
    private UserResponse organizer;
    private FootballPitchResponse pitch;
    private List<TeamResponse> teams;

    public FootballMatchResponseDetails(FootballMatch footballMatch) {
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
