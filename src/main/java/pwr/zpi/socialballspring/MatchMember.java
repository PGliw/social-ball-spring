package pwr.zpi.socialballspring;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class MatchMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean isConfirmed;
    @ManyToOne()
    private User user;
    @OneToMany(targetEntity = Match.class, mappedBy = "matchMember")
    private List<Match> appearancesOnMatches;
}
