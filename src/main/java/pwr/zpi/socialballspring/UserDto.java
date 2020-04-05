package pwr.zpi.socialballspring;

import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MatchMember> getAppearancesAsMatchMember() {
        return appearancesAsMatchMember;
    }

    public void setAppearancesAsMatchMember(List<MatchMember> appearancesAsMatchMember) {
        this.appearancesAsMatchMember = appearancesAsMatchMember;
    }
}
