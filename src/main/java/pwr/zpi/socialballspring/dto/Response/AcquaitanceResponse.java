package pwr.zpi.socialballspring.dto.Response;

import pwr.zpi.socialballspring.model.Acquaintance;

public class AcquaitanceResponse {
    private Long id;
    private String dateOfAcceptance;
    private String status;
    private Long requestSenderId;
    private Long requestReceiverId;

    public AcquaitanceResponse(Acquaintance acquaintance){
        this.id = acquaintance.getId();
        if(acquaintance.getDateOfAcceptance() != null) {
            this.dateOfAcceptance = acquaintance.getDateOfAcceptance().toString();
        }
        this.status = acquaintance.getStatus();
        if(acquaintance.getRequestReceiver() != null){
            this.requestReceiverId = acquaintance.getRequestReceiver().getId();
        }
        if(acquaintance.getRequestSender() != null){
            this.requestSenderId = acquaintance.getRequestSender().getId();
        }
    }
}
