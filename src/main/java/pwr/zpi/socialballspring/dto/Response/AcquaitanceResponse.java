package pwr.zpi.socialballspring.dto.Response;

import pwr.zpi.socialballspring.model.Acquaintance;

public class AcquaitanceResponse {
    private Long id;
    private String dateOfAcceptance;
    private String status;
    private UserResponse requestSenderId;
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
            this.requestSenderId = new UserResponse(acquaintance.getRequestSender());
        }
    }

    public Long getId() {
        return id;
    }

    public String getDateOfAcceptance() {
        return dateOfAcceptance;
    }

    public String getStatus() {
        return status;
    }

    public UserResponse getRequestSenderId() {
        return requestSenderId;
    }

    public Long getRequestReceiverId() {
        return requestReceiverId;
    }
}
