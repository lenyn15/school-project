package school.project.servicestudent.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private final Pattern mask = Pattern.compile( "[0-9]{9}" );

    @Override
    public void initialize( Phone constraintAnnotation ) {
        ConstraintValidator.super.initialize( constraintAnnotation );
    }

    @Override
    public boolean isValid( String value,
                            ConstraintValidatorContext context ) {
        final Matcher matcher = mask.matcher( value );
        return matcher.matches();
    }
}
