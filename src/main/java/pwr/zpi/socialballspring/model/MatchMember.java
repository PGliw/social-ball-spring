package pwr.zpi.socialballspring.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
public class MatchMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean isConfirmed;


    @ManyToOne
    private User user;
    @ManyToOne
    private Position position;
    @ManyToOne
    private Team team;
    @ManyToOne
    private FootballMatch footballMatch;
    @OneToMany(targetEntity = Rating.class, mappedBy = "sender")
    private List<Rating> ratingsSent;
    @OneToMany(targetEntity = Rating.class, mappedBy = "receiver")
    private List<Rating> ratingsReceived;
    @OneToMany(targetEntity = Comment.class, mappedBy = "relatedMatchMember")
    private List<Comment> matchCommentsAdded;
    @OneToMany(targetEntity = Event.class, mappedBy = "matchMember")
    private List<Event> eventsInvolvingMatchMember;

    @Tolerate
    public MatchMember(){
    }
}

