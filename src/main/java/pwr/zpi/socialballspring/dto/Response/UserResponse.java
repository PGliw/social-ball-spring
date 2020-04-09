package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.User;


@Data
public class UserResponse {
    /**
     * Response of HTTP method that returns user info
     */
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String username;
    private String email;
    private String image;

    public UserResponse(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.dateOfBirth = user.getDateOfBirth().toString();
        this.image = user.getImage();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
