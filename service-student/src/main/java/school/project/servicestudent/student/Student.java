package school.project.servicestudent.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import school.project.servicestudent.tutor.Tutor;
import school.project.servicestudent.validation.Dni;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

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
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Student implements Serializable {

    @Id
    @GeneratedValue( strategy = AUTO )
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

    @NotEmpty( message = "Seleccione el sexo del estudiante" )
    @Column( name = "sexo",
             nullable = false,
             length = 20 )
    private String gender;

    @Dni
    @Size( min = 8,
           max = 8,
           message = "El tamaño del dni es 8" )
    @Column( nullable = false,
             length = 8 )
    private String dni;

    @Past( message = "La fecha ingresada, debe ser menor a la actual" )
    @Column( name = "fecha_nacimiento",
             nullable = false )
    private Date birth_date;

    @NotEmpty( message = "Ingrese la direccion del estudiante" )
    @Column( name = "direccion",
             nullable = false,
             length = 60 )
    private String address;

    @Column( name = "estado",
             length = 20 )
    private String status;

    @ToString.Exclude
    @ManyToOne( fetch = LAZY )
    @JoinColumn( name = "apoderado_id",
                 foreignKey = @ForeignKey( name = "fk_apoderado" ) )
    @JsonIgnoreProperties( {
            "hibernateLazyInitializer",
            "handler"
    } )
    private Tutor tutor;
}
