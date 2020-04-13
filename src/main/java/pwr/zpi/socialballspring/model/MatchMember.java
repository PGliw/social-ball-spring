package pwr.zpi.socialballspring.model;

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
    @ManyToOne()
    private Position position;
    @ManyToOne()
    private Team team;
    @OneToMany(targetEntity = FootballMatch.class, mappedBy = "matchMember")
    private List<FootballMatch> appearancesOnMatches;
    @OneToMany(targetEntity = Rating.class, mappedBy = "sender")
    private List<Rating> ratingsSent;
    @OneToMany(targetEntity = Rating.class, mappedBy = "receiver")
    private List<Rating> ratingsReceived;
    @OneToMany(targetEntity = Comment.class, mappedBy = "relatedMatchMember")
    private List<Comment> matchCommentsAdded;
    @OneToMany(targetEntity = Event.class, mappedBy = "matchMember")
    private List<Event> eventsInvolvingMatchMember;
}