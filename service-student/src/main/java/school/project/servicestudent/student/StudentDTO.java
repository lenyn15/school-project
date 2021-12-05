package school.project.servicestudent.student;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import school.project.servicestudent.enums.Status;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonInclude( NON_NULL )
public class StudentDTO {
    protected Long id;
    protected String name;
    protected String surname;
    protected String complete_name;
    protected Long id_gender;
    protected String gender;
    protected String dni;
    protected String birth_date;
    protected LocalDate birthDate;
    protected String address;
    protected Status status;
    protected Long id_tutor;
}
