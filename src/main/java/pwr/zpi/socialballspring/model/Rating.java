package pwr.zpi.socialballspring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rating;
    private String comment;
    private boolean isAbuseReported;

    @ManyToOne()
    private MatchMember sender;
    @ManyToOne()
    private MatchMember receiver;
}
