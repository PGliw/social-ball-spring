package pwr.zpi.socialballspring.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@Getter
@NoArgsConstructor
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateException extends RuntimeException {

    public DuplicateException(String entityName) {
        super(entityName+ " already exists.");
    }
}
