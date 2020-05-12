package pwr.zpi.socialballspring.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateOfAddition;
    private String content;

    @ManyToOne()
    private FootballMatch relatedMatch;
    @ManyToOne()
    private MatchMember relatedMatchMember;

    @Tolerate
    public Comment(){
    }
}
