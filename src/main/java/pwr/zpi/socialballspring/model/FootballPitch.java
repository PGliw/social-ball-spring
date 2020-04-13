package pwr.zpi.socialballspring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class FootballPitch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    private boolean isPayable;
    private boolean isReservationRequired;
    private String typeOfSurface;
    private String website;
    private String image;

    @OneToMany(targetEntity = FootballMatch.class, mappedBy = "footballPitch")
    private List<FootballMatch> relatedFootballMatches;
}
