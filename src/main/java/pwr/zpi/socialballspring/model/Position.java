package pwr.zpi.socialballspring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(targetEntity = FavouritePosition.class, mappedBy = "position")
    private List<FavouritePosition> relatedFavouritePositions;
    @OneToMany(targetEntity = MatchMember.class, mappedBy = "position")
    private List<MatchMember> relatedMatchMembers;
}
