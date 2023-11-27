package az.iktlab.taskmanagement.error;

import az.iktlab.taskmanagement.error.exception.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleAuthenticationException(AuthenticationException authenticationException) {
        return new ErrorResponse(HttpStatus.valueOf(401),authenticationException.getMessage());
    }

}
