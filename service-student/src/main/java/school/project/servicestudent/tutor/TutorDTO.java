package school.project.servicestudent.tutor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import school.project.servicestudent.enums.Status;
import school.project.servicestudent.student.StudentDTO;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude( NON_NULL )
public class TutorDTO {
    protected Long id;
    protected String name;
    protected String surname;
    protected String complete_name;
    protected Long id_gender;
    protected String gender;
    protected String dni;
    protected String phone;
    protected String email;
    protected String occupation;
    protected Status status;
    protected List<StudentDTO> students;
}
