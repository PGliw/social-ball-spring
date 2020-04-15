package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.FootballPitch;

@Data
public class FootballPitchResponse {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Boolean isPayable;
    private Boolean isReservationRequired;
    private String typeOfSurface;
    private String website;
    private String image;

    public FootballPitchResponse(FootballPitch footballPitch) {
        this.id = footballPitch.getId();
        this.name = footballPitch.getName();
        this.latitude = footballPitch.getLatitude();
        this.longitude = footballPitch.getLongitude();
        this.isPayable = footballPitch.isPayable();
        this.isReservationRequired = footballPitch.isReservationRequired();
        this.typeOfSurface = footballPitch.getTypeOfSurface();
        this.website = footballPitch.getWebsite();
        this.image = footballPitch.getImage();
    }
}
