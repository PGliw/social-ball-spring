package pwr.zpi.socialballspring.dto;

import lombok.Data;

@Data
public class MatchMemberDto {
    private Long id;
    private boolean isConfirmed;
    private Long userId;
    private Long positionId;
    private Long teamId;
}
