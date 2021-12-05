package school.project.servicestudent.tutor;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.project.servicestudent.enums.Gender;
import school.project.servicestudent.student.Student;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;
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

    @NotEmpty( message = "Ingrese el nombre del apoderado" )
    @Column( name = "nombres",
             nullable = false,
             length = 50 )
    private String name;

    @NotEmpty( message = "Ingrese los apelllidos del apoderado" )
    @Column( name = "apellidos",
             nullable = false,
             length = 45 )
    private String surname;

    @NotNull
    @Enumerated( STRING )
    @Column( name = "sexo",
             nullable = false )
    private Gender gender;

    @NotEmpty( message = "Ingrese el dni del apoderado" )
    @Size( min = 8,
           max = 8,
           message = "El tamaño del DNI es 8" )
    @Column( nullable = false,
             length = 8 )
    private String dni;

    @NotEmpty( message = "Ingrese el telefono del apoderado" )
    @Size( min = 9,
           max = 9,
           message = "El tamaño del telefono es 9" )
    @Column( name = "telefono",
             nullable = false,
             length = 9 )
    private String phone;

    @Email( message = "El email ingresado no es invalido" )
    @NotEmpty( message = "Ingrese el email del apoderado" )
    @Column( nullable = false,
             length = 65 )
    private String email;

    @NotEmpty( message = "Ingrese el trabajo del apoderado" )
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
