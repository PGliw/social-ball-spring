package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.FavouritePosition;

@Data
public class FavouritePositionResponse {
    private Long id;
    private Long userId;
    private PositionResponse positionId;

    public FavouritePositionResponse(FavouritePosition favouritePosition){
        this.id = favouritePosition.getId();
        if(favouritePosition.getPosition().getId() != null){
            this.positionId = new PositionResponse(favouritePosition.getPosition());
        }
        if(favouritePosition.getUser().getId() != null){
            this.userId = favouritePosition.getUser().getId();
        }
    }
}
