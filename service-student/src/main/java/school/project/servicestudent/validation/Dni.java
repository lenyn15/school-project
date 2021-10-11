package school.project.servicestudent.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( {
        METHOD,
        FIELD,
        ANNOTATION_TYPE
} )
@Retention( RUNTIME )
@Constraint( validatedBy = DniValidator.class )
@Documented
public @interface Dni {
    String message() default "El dni ingresado, es invalido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
