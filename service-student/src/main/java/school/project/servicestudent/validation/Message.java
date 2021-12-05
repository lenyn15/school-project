package school.project.servicestudent.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static school.project.servicestudent.validation.ErrorMessage.*;

public class Message {

    public static String formatMessage( BindingResult result ) {
        List<Map<String, String>> errors = result.getFieldErrors().stream().map( err -> {
            Map<String, String> error = new HashMap<>();
            error.put( err.getField(), err.getDefaultMessage() );
            return error;
        } ).collect( toList() );

        ErrorMessage errorMessage = builder().code( "01" ).messages( errors ).build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString( errorMessage );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
