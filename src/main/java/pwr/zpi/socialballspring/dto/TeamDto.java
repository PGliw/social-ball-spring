package pwr.zpi.socialballspring.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private String name;
    private Integer membersCount;
    private String shirtColours;
    private Long footballMatchId;
    private List<MatchMemberDto> teamMembers;
}
