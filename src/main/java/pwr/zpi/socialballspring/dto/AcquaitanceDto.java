package pwr.zpi.socialballspring.dto;

import lombok.Data;

@Data
public class AcquaitanceDto {
    private Long id;
    private String dateOfAcceptance;
    private String status;
    private Long requestSenderId;
    private Long requestReceiverId;
}
