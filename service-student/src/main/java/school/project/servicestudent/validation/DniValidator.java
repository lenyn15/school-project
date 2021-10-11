package school.project.servicestudent.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DniValidator implements ConstraintValidator<Dni, String> {

    private final Pattern mask = Pattern.compile( "[0-9]{8}" );

    @Override
    public void initialize( Dni constraintAnnotation ) {
        ConstraintValidator.super.initialize( constraintAnnotation );
    }

    @Override
    public boolean isValid( String value,
                            ConstraintValidatorContext context ) {
        final Matcher matcher = mask.matcher( value );
        return matcher.matches();
    }
}
