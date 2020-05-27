package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.FootballPitch;
import pwr.zpi.socialballspring.model.Team;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FootballMatchResponse {
    private Long id;
    private String beginningTime;
    private String endingTime;
    private String description;
    private Long organizerId;
    private FootballPitchResponse pitch;
    private List<TeamResponse> teams;

    public FootballMatchResponse(FootballMatch footballMatch){
        this.id = footballMatch.getId();
        if(footballMatch.getBeginningTime() != null) {
            this.beginningTime = footballMatch.getBeginningTime().toString();
        }
        if(footballMatch.getEndingTime() != null) {
            this.endingTime = footballMatch.getEndingTime().toString();
        }
        this.description = footballMatch.getDescription();
        if(footballMatch.getOrganizer() != null) {
            this.organizerId = footballMatch.getOrganizer().getId();
        }
        if(footballMatch.getFootballPitch() != null){
            this.pitch = new FootballPitchResponse(footballMatch.getFootballPitch());
        }
        if(footballMatch.getMatchMembers() != null){
            this.teams = footballMatch.getTeamsInvolved().stream().map(TeamResponse::new).collect(Collectors.toList());
        }
    }
}
