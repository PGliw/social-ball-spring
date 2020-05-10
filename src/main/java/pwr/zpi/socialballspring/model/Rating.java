package pwr.zpi.socialballspring.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int ratingMark;
    private String comment;
    private boolean isAbuseReported;

    @ManyToOne()
    private MatchMember sender;
    @ManyToOne()
    private MatchMember receiver;

    @Tolerate
    public Rating(){
    }
}
