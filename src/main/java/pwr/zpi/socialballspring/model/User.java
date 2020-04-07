package pwr.zpi.socialballspring.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Tolerate;
import pwr.zpi.socialballspring.model.MatchMember;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
public class User {
    @Tolerate
    public User() {} // no args constructor is required by Hibernate
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String image;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private String password;
    @NotNull
    @Column(unique = true)
    private String email;
    private String username;
    @OneToMany(targetEntity = MatchMember.class, mappedBy = "user")
    private List<MatchMember> appearancesAsMatchMember;
}
