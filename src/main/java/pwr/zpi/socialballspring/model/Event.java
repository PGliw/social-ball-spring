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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private LocalDateTime dateTime;

    @ManyToOne()
    private MatchMember matchMember;
    @ManyToOne()
    private FootballMatch footballMatch;


    @Tolerate
    public Event(){
    }
}
