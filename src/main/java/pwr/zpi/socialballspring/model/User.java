package pwr.zpi.socialballspring.model;

import lombok.Getter;
import lombok.Setter;
import pwr.zpi.socialballspring.model.MatchMember;

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
    private String firstName;
    private String lastName;
    private String image;
    private LocalDate dateOfBirth;
    private String password;
    private String email;
    private String username;
    @OneToMany(targetEntity = MatchMember.class, mappedBy = "user")
    private List<MatchMember> appearancesAsMatchMember;
}
