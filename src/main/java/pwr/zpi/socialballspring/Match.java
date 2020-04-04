package pwr.zpi.socialballspring;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate beginningTime;
    private LocalDate endingTime;
    private boolean areTeamsPermament;
    @ManyToOne()
    private MatchMember matchMember;
}
