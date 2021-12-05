package school.project.servicestudent.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(
        String message,
        Throwable throwable,
        HttpStatus status,
        ZonedDateTime zonedDateTime
) {

}
