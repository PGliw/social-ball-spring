package pwr.zpi.socialballspring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "side"})
)
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name; // goalkeeper | defender | midfielder | forward
    private String side; // null | left | middle-left | middle | middle-right | right

    @OneToMany(targetEntity = FavouritePosition.class, mappedBy = "position")
    private List<FavouritePosition> relatedFavouritePositions;
    @OneToMany(targetEntity = MatchMember.class, mappedBy = "position")
    private List<MatchMember> relatedMatchMembers;
}
