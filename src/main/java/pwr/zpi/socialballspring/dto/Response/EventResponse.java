package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.Event;

@Data
public class EventResponse {
    private Long id;
    private String type;
    private String dateTime;
    private MatchMemberResponse matchMemberResponse;
    private Long teamId;
    private String teamName;
    private Long footballMatchId;

    public EventResponse(Event event) {
        this.id = event.getId();
        this.type = event.getType();
        if (event.getMatchMember() != null) {
            this.matchMemberResponse = new MatchMemberResponse(event.getMatchMember());
            this.teamId = event.getMatchMember().getTeam().getId();
            this.teamName = event.getMatchMember().getTeam().getName();
        }
        if (event.getFootballMatch() != null) {
            this.footballMatchId = event.getFootballMatch().getId();
        }
    }
}
