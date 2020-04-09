package pwr.zpi.socialballspring.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class FootballMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate beginningTime;
    private LocalDate endingTime;
    @ManyToOne()
    private MatchMember matchMember;
}
