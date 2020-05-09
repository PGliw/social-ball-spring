package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.FootballMatch;

@Data
public class FootballMatchResponse {
    private Long id;
    private String beginningTime;
    private String endingTime;
    private String description;
    private Long organizerId;
    private Long pitchId;

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
            this.pitchId = footballMatch.getFootballPitch().getId();
        }
    }
}
