package pwr.zpi.socialballspring.dto.Response;

import pwr.zpi.socialballspring.model.FavouritePosition;

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

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public PositionResponse getPositionId() {
        return positionId;
    }
}
