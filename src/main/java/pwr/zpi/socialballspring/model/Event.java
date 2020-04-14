package pwr.zpi.socialballspring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;

    @ManyToOne()
    private MatchMember matchMember;
    @ManyToOne()
    private FootballMatch footballMatch;
}
