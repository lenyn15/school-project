package school.project.servicestudent.tutor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.project.servicestudent.enums.Status;

@Getter
@Setter
@NoArgsConstructor
public class TutorDTO {
    private Long id;
    private String name;
    private String surname;
    private String complete_name;
    private int id_gender;
    private String gender;
    private String dni;
    private String phone;
    private String email;
    private String occupation;
    private Status status;
}
