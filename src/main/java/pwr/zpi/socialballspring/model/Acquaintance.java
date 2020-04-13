package pwr.zpi.socialballspring.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Acquaintance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate dateOfAcceptance;
    private String status;

    @ManyToOne()
    private User requestSender;
    @ManyToOne()
    private User requestReceiver;
}
