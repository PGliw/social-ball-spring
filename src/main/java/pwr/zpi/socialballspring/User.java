package pwr.zpi.socialballspring;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String image;
    private LocalDate dateOfBirth;
    private char[] password;
    private String email;
    private String pseudonym;
    @OneToMany(targetEntity = MatchMember.class, mappedBy = "user")
    private List<MatchMember> appearancesAsMatchMember;
}
