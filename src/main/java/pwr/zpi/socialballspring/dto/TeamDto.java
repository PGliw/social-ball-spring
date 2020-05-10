package pwr.zpi.socialballspring.dto;

import lombok.Data;

@Data
public class TeamDto {
    private Long id;
    private Integer membersCount;
    private String shirtColours;
    private Long footballMatchId;
}
