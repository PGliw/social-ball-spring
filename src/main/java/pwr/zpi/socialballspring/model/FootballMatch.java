package pwr.zpi.socialballspring.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private String title;
    private String description;
    private Boolean isFinished;
    private Boolean hasProtocol;
    private String matchScore;

    @ManyToOne
    private User organizer;
    @OneToMany(targetEntity = MatchMember.class, mappedBy = "footballMatch")
    private List<MatchMember> matchMembers;
    @ManyToOne
    private FootballPitch footballPitch;
    @OneToMany(targetEntity = Comment.class, mappedBy = "relatedMatch")
    private List<Comment> relatedComments;
    @OneToMany(targetEntity = Event.class, mappedBy = "footballMatch")
    private List<Event> eventsInvolvingMatch;

    @Getter(AccessLevel.NONE) // for custom getter
    @OneToMany(targetEntity = Team.class, mappedBy = "footballMatch")
    private List<Team> teamsInvolved;

    // custom getter that sorts teams in order [left team, right team]
    public List<Team> getTeamsInvolved() {
        if (teamsInvolved != null && teamsInvolved.size() == 2 && teamsInvolved.get(0) != null && teamsInvolved.get(1) != null) {
            final ArrayList<Team> orderedTeams = new ArrayList<>();
            int indexOfLeftTeam = 0;
            if (teamsInvolved.get(1).getIsLeftSide() != null && teamsInvolved.get(1).getIsLeftSide()) {
                indexOfLeftTeam = 1;
            }
            int indexOfRightTeam = (indexOfLeftTeam + 1) % 2;
            orderedTeams.add(teamsInvolved.get(indexOfLeftTeam));
            orderedTeams.add(teamsInvolved.get(indexOfRightTeam));
            return orderedTeams;
        }
        return teamsInvolved;
    }

    @Tolerate
    public FootballMatch() {
    }
}


