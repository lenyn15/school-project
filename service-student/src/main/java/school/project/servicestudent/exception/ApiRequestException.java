package school.project.servicestudent.exception;

public class ApiRequestException extends RuntimeException{

    public ApiRequestException( String message ) {
        super( message );
    }
}
