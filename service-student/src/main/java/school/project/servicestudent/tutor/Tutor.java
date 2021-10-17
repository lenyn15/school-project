package school.project.servicestudent.tutor;

import lombok.*;
import school.project.servicestudent.validation.Dni;
import school.project.servicestudent.validation.Phone;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static javax.persistence.GenerationType.*;

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
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Tutor implements Serializable {

    @Id
    @GeneratedValue( strategy = AUTO )
    @Column( nullable = false,
             updatable = false )
    private int id;

    @NotEmpty( message = "Ingrese el nombre del apoderado" )
    @Column( name = "nombres",
             nullable = false,
             length = 45 )
    private String name;

    @NotEmpty( message = "Ingrese los apelllidos del apoderado" )
    @Column( name = "apellidos",
             nullable = false,
             length = 45 )
    private String surname;

    @NotEmpty( message = "Seleccione el sexo del apoderado" )
    @Column( name = "sexo",
             nullable = false,
             length = 10 )
    private String gender;

    @Dni
    @NotEmpty( message = "Ingrese el dni del apoderado" )
    @Column( nullable = false,
             length = 8 )
    private String dni;

    @Phone
    @NotEmpty( message = "Ingrese el telefono del apoderado" )
    @Column( name = "telefono",
             nullable = false,
             length = 9 )
    private String phone;

    @Email( message = "El email ingresado, es invalido" )
    @NotEmpty( message = "Ingrese el email del apoderado" )
    @Column( nullable = false,
             length = 65 )
    private String email;

    @NotEmpty( message = "Ingrese el trabajo del apoderado" )
    @Column( name = "trabajo",
             nullable = false,
             length = 45 )
    private String occupation;
}
