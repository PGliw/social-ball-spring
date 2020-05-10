package pwr.zpi.socialballspring.dto.Response;

import pwr.zpi.socialballspring.model.Position;

public class PositionResponse {
    private Long id;
    private String name;
    private String side;

    public PositionResponse(Position position){
        this.id = position.getId();
        this.name = position.getName();
        this.side = position.getSide();
    }
}
