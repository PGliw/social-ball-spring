package pwr.zpi.socialballspring.dto;

import lombok.Data;

@Data
public class FootballPitchDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Boolean isPayable;
    private Boolean isReservationRequired;
    private String typeOfSurface;
    private String website;
    private String image;
}
