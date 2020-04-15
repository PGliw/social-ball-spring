package pwr.zpi.socialballspring.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Data
@Builder
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

    @Tolerate
    public FootballPitch(){
    }
}
