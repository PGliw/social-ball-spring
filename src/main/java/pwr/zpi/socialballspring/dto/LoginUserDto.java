package pwr.zpi.socialballspring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserDto {

    private String email;
    private String password;
}