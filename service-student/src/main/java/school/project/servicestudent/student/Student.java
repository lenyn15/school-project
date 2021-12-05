package school.project.servicestudent.student;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.project.servicestudent.enums.Gender;
import school.project.servicestudent.tutor.Tutor;

import javax.persistence.*;
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

    @Column( name = "nombres",
             nullable = false,
             length = 45 )
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

    @Column( name = "fecha_nacimiento",
             nullable = false )
    private LocalDate birth_date;

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
