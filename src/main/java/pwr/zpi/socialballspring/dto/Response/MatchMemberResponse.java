package pwr.zpi.socialballspring.dto.Response;

import pwr.zpi.socialballspring.model.MatchMember;

public class MatchMemberResponse {
    private Long id;
    private boolean isConfirmed;
    private Long userId;
    private Long positionId;
    private Long teamId;

    public MatchMemberResponse(MatchMember matchMember){
        this.id = matchMember.getId();
        this.isConfirmed = matchMember.isConfirmed();
        if(matchMember.getUser() != null){
            this.userId = matchMember.getUser().getId();
        }
        if(matchMember.getTeam() != null){
            this.teamId = matchMember.getTeam().getId();
        }
        if(matchMember.getPosition() != null){
            this.positionId = matchMember.getPosition().getId();
        }
    }
}
