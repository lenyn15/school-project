package school.project.servicestudent.student;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.project.servicestudent.enums.Gender;
import school.project.servicestudent.tutor.Tutor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table( name = "estudiante",
        indexes = {
                @Index( name = "index_id",
                        columnList = "id" )
        },
        uniqueConstraints = {
                @UniqueConstraint( name = "dni_unique",
                                   columnNames = "dni" )
        } )
@NoArgsConstructor
@Getter
@Setter
public class Student implements Serializable {

    @Id
    @GeneratedValue( strategy = IDENTITY )
    @Column( nullable = false,
             updatable = false )
    private Long id;

    @NotEmpty( message = "Ingrese el nombre del estudiante" )
    @Column( name = "nombres",
             nullable = false,
             length = 45 )
    private String name;

    @NotEmpty( message = "Ingrese los apellidos del estudiante" )
    @Column( name = "apellidos",
             nullable = false,
             length = 45 )
    private String surname;

    @NotNull
    @Enumerated( STRING )
    @Column( name = "sexo",
             nullable = false )
    private Gender gender;

    @NotEmpty( message = "Ingrese el dni del estudiante" )
    @Size( min = 8,
           max = 8,
           message = "El tamaño del dni es 8" )
    @Column( nullable = false,
             length = 8 )
    private String dni;

    @Past( message = "La fecha ingresada, debe ser menor a la actual" )
    @Column( name = "fecha_nacimiento",
             nullable = false )
    private LocalDate birth_date;

    @NotEmpty( message = "Ingrese la direccion del estudiante" )
    @Column( name = "direccion",
             nullable = false,
             length = 60 )
    private String address;

    @Column( name = "estado",
             length = 20 )
    private Boolean status;

    @ManyToOne
    @JsonBackReference
    @JoinColumn( name = "apoderado_id",
                 foreignKey = @ForeignKey( name = "fk_apoderado" ),
                 nullable = false )
    private Tutor tutor;
}
