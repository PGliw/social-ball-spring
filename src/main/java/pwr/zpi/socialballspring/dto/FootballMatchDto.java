package pwr.zpi.socialballspring.dto;

import lombok.Data;

@Data
public class FootballMatchDto {
    private Long id;
    private String beginningTime;
    private String endingTime;
    private String description;
    private Long pitchId;
}
