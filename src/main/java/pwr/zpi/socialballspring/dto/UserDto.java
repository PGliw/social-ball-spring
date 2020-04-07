package pwr.zpi.socialballspring.dto;

import lombok.Data;
import pwr.zpi.socialballspring.model.MatchMember;

import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
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
