package pwr.zpi.socialballspring.dto.Response;

import pwr.zpi.socialballspring.model.Team;

public class TeamResponse {
    private Long id;
    private Integer membersCount;
    private String shirtColours;
    private Long footballMatchId;

    public TeamResponse(Team team){
        this.id = team.getId();
        this.membersCount = team.getMembersCount();
        this.shirtColours = team.getShirtColours();
        if(team.getFootballMatch() != null){
            this.footballMatchId = team.getFootballMatch().getId();
        }
    }
}
