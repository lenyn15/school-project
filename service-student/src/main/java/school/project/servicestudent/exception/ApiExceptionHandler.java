package school.project.servicestudent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler( value = { ApiRequestException.class } )
    public ResponseEntity<Object> handleApiRequestException( ApiRequestException exception ) {
        HttpStatus badRequest = BAD_REQUEST;
        ApiException requestException = new ApiException( exception.getMessage(), exception, badRequest,
                                                          ZonedDateTime.now() );
        return new ResponseEntity<>( requestException, badRequest );
    }
}
