package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.Acquaintance;

@Data
public class AcquaitanceResponse {
    private Long id;
    private String dateOfAcceptance;
    private String status;
    private UserResponse requestSender;
    private UserResponse requestReceiver;

    public AcquaitanceResponse(Acquaintance acquaintance){
        this.id = acquaintance.getId();
        if(acquaintance.getDateOfAcceptance() != null) {
            this.dateOfAcceptance = acquaintance.getDateOfAcceptance().toString();
        }
        this.status = acquaintance.getStatus();
        if(acquaintance.getRequestReceiver() != null){
            this.requestReceiver = new UserResponse(acquaintance.getRequestReceiver());
        }
        if(acquaintance.getRequestSender() != null){
            this.requestSender = new UserResponse(acquaintance.getRequestSender());
        }
    }
}
