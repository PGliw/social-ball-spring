package pwr.zpi.socialballspring.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String image;
    private LocalDate dateOfBirth;
    private char[] password;
    private String email;
    private String username;
}
