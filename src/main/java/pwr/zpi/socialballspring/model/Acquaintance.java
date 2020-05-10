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
public class Acquaintance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateOfAcceptance;
    private String status;

    @ManyToOne()
    private User requestSender;
    @ManyToOne()
    private User requestReceiver;

    @Tolerate
    public Acquaintance(){
    }
}
