package pwr.zpi.socialballspring.model;

import lombok.AccessLevel;
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
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int membersCount;
    private String name;
    private String shirtColours;
    private Boolean isLeftSide;

    @ManyToOne()
    private FootballMatch footballMatch;
    @OneToMany(targetEntity = MatchMember.class, mappedBy = "team")
    private List<MatchMember> teamMembers;

    @Tolerate
    public Team(){
    }
}
