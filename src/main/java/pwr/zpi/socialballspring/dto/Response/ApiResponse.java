package pwr.zpi.socialballspring.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private int status;
    private String message;
    private Object result;
}
