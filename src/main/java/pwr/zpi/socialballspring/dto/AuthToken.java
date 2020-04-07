package pwr.zpi.socialballspring.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private String token;
    private String username;
}
