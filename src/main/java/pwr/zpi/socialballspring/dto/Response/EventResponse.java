package pwr.zpi.socialballspring.dto.Response;

import pwr.zpi.socialballspring.model.Event;

public class EventResponse {
    private Long id;
    private String type;
    private Long matchMemberId;
    private Long footballMatchId;

    public EventResponse(Event event){
        this.id = event.getId();
        this.type = event.getType();
        if(event.getMatchMember() != null){
            this.matchMemberId = event.getMatchMember().getId();
        }
        if(event.getFootballMatch() != null){
            this.footballMatchId = event.getFootballMatch().getId();
        }
    }
}
