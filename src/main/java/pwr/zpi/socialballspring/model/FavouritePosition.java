package pwr.zpi.socialballspring.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
public class FavouritePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    private User user;
    @ManyToOne()
    private Position position;

    @Tolerate
    public FavouritePosition(){
    }
}
