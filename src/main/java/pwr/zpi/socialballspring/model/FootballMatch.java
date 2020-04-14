package pwr.zpi.socialballspring.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


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
    @ManyToOne()
    private FootballPitch footballPitch;
    @OneToMany(targetEntity = Comment.class, mappedBy = "relatedMatch")
    private List<Comment> relatedComments;
    @OneToMany(targetEntity = Event.class, mappedBy = "footballMatch")
    private List<Event> eventsInvolvingMatch;
    @OneToMany(targetEntity = Team.class, mappedBy = "footballMatch")
    private List<Team> teamsInvolved;
}


