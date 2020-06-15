package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;

@Data
public class UserAcquaitanceResponse {
    private Boolean isAcquaitanceSent;

    public UserAcquaitanceResponse(Boolean isUserAcquaitance){
        this.isAcquaitanceSent = isUserAcquaitance;
    }
}
