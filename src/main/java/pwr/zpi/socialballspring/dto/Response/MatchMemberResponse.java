package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.MatchMember;

@Data
public class MatchMemberResponse {
    private Long id;
    private boolean isConfirmed;
    private Long positionId;
    private Long teamId;
    private UserResponse user;

    public MatchMemberResponse(MatchMember matchMember){
        this.id = matchMember.getId();
        this.isConfirmed = matchMember.isConfirmed();
        if(matchMember.getUser() != null){
            this.user = new UserResponse(matchMember.getUser());
        }
        if(matchMember.getTeam() != null){
            this.teamId = matchMember.getTeam().getId();
        }
        if(matchMember.getPosition() != null){
            this.positionId = matchMember.getPosition().getId();
        }
    }
}
