package school.project.servicestudent.tutor;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.project.servicestudent.enums.Gender;
import school.project.servicestudent.student.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table( name = "apoderado",
        indexes = {
                @Index( name = "index_id",
                        columnList = "id" )
        },
        uniqueConstraints = {
                @UniqueConstraint( name = "dni_unique",
                                   columnNames = "dni" ),
                @UniqueConstraint( name = "email_unique",
                                   columnNames = "email" )
        } )
@NoArgsConstructor
@Setter
@Getter
public class Tutor implements Serializable {

    @Id
    @GeneratedValue( strategy = IDENTITY )
    @Column( nullable = false,
             updatable = false )
    private Long id;

    @Column( name = "nombres",
             nullable = false,
             length = 50 )
    private String name;

    @Column( name = "apellidos",
             nullable = false,
             length = 45 )
    private String surname;

    @Enumerated( STRING )
    @Column( name = "sexo",
             nullable = false )
    private Gender gender;

    @Column( nullable = false,
             length = 8 )
    private String dni;

    @Column( name = "telefono",
             nullable = false,
             length = 9 )
    private String phone;

    @Column( nullable = false,
             length = 65 )
    private String email;

    @Column( name = "trabajo",
             nullable = false,
             length = 45 )
    private String occupation;

    @Column( name = "estado",
             nullable = false )
    private Boolean status;

    @OneToMany( fetch = EAGER,
                mappedBy = "tutor",
                cascade = ALL )
    @JsonManagedReference
    private List<Student> students;
}
