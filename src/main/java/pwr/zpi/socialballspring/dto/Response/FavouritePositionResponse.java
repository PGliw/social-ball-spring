package pwr.zpi.socialballspring.dto.Response;

import pwr.zpi.socialballspring.model.FavouritePosition;

public class FavouritePositionResponse {
    private Long id;
    private Long userId;
    private Long positionId;

    public FavouritePositionResponse(FavouritePosition favouritePosition){
        this.id = favouritePosition.getId();
        if(favouritePosition.getPosition().getId() != null){
            this.positionId = favouritePosition.getPosition().getId();
        }
        if(favouritePosition.getUser().getId() != null){
            this.userId = favouritePosition.getUser().getId();
        }
    }
}
