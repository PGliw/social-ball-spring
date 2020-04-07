package pwr.zpi.socialballspring.dto.Response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResopnse {
    private String token;
    private String username;
}
