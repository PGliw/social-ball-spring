package pwr.zpi.socialballspring.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@Getter
@NoArgsConstructor
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class InvalidCredentialsException extends RuntimeException {
    InvalidCredentialsException(String message) {
        super(message);
    }
}
