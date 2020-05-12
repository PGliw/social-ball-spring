package pwr.zpi.socialballspring.dto;

import lombok.Data;

@Data
public class EventDto {
    private Long id;
    private String type;
    private Long matchMemberId;
    private Long footballMatchId;
}
