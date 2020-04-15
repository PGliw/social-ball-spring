package pwr.zpi.socialballspring.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
public class FootballMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime beginningTime;
    private LocalDateTime endingTime;
    private String description;


    @ManyToOne()
    private User organizer;
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

    @Tolerate
    public FootballMatch() {
    }
}


