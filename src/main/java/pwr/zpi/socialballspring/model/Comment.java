package pwr.zpi.socialballspring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate dateOfAddition;
    private String content;

    @ManyToOne()
    private FootballMatch relatedMatch;
    @ManyToOne()
    private MatchMember relatedMatchMember;
}
