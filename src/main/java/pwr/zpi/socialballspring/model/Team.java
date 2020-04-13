package pwr.zpi.socialballspring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int membersCount;
    private String shirtColours;

    @ManyToOne()
    private FootballMatch footballMatch;
    @OneToMany(targetEntity = MatchMember.class, mappedBy = "team")
    private List<MatchMember> teamMembers;
}
