package school.project.servicestudent.validation;

import org.springframework.stereotype.Component;
import school.project.servicestudent.student.Student;
import school.project.servicestudent.student.StudentDTO;
import school.project.servicestudent.student.StudentRepository;
import school.project.servicestudent.tutor.Tutor;
import school.project.servicestudent.tutor.TutorRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.time.Period.between;
import static java.util.stream.Collectors.toList;
import static school.project.servicestudent.enums.Gender.FEMENINO;
import static school.project.servicestudent.enums.Gender.MASCULINO;
import static school.project.servicestudent.enums.Status.HABILITADO;
import static school.project.servicestudent.enums.Status.INHABILITADO;
import static school.project.servicestudent.student.StudentDTO.*;

@Component
public record StudentMethods(
        StudentRepository studentRepository,
        TutorRepository tutorRepository
) {

    public String validate( StudentDTO studentDTO, String action ) {
        String message = "";
        Student studentDB = new Student();
        if ( Objects.equals( action, "update" ) ) {
            studentDB = studentRepository.findById( studentDTO.getId() ).orElse( null );
            if ( studentDB == null ) {
                return format( "El estudiante con id %d no existe", studentDTO.getId() );
            }
        }

        // Valida el nombre y apellido del estudiante
        if ( !Objects.equals( studentDB.getName(), studentDTO.getName() ) &&
                !Objects.equals( studentDB.getSurname(), studentDTO.getSurname() ) ) {
            Optional<Student> complete_nameDB = studentRepository.findByNameAndSurname( studentDTO.getName(), studentDTO.getSurname() );
            if ( complete_nameDB.isPresent() ) {
                return format( "Estudiante con nombre %s %s ya existe", studentDTO.getName(), studentDTO.getSurname() );
            }
            if ( studentDTO.getName().isEmpty() || studentDTO.getName() == null ) {
                return "El campo nombre del estudiante esta vacío";
            }
            if ( studentDTO.getSurname().isEmpty() || studentDTO.getSurname() == null ) {
                return "El campo apellido del estudiante esta vacío";
            }
        }

        // Valida el DNI del estudiante
        if ( !Objects.equals( studentDB.getDni(), studentDTO.getDni() ) ) {
            Boolean existDni = studentRepository.existDni( studentDTO.getDni() );
            if ( existDni ) {
                return "El DNI ingresado ya existe, ingrese otro";
            }
            if ( studentDTO.getDni().isEmpty() || studentDTO.getDni() == null ) {
                return "El campo DNI del estudiante esta vacío";
            }
            if ( studentDTO.getDni().length() != 8 ) {
                return "El campo DNI debe ser de tamaño 8";
            }
        }

        // Valida la edad y fecha de nacimiento del estudiante
        if ( studentDTO.getBirthDate() == null ) {
            return "El campo Fecha de nacimiento del estudiante esta vacío";
        }
        int years = between( studentDTO.getBirthDate(), now() ).getYears();
        if ( years < 6 ) {
            return "La edad minima requerida son 6 años";
        }

        // Valida la direccion del estudiante
        if ( studentDTO.getAddress().isEmpty() || studentDTO.getAddress() == null ) {
            return "El campo dirección del estudiante esta vacío";
        }

        // Valida el apoderado del estudiante
        Tutor tutor = tutorRepository.findById( studentDTO.getId_tutor() ).orElse( null );
        if ( tutor == null ) {
            return format( "El apoderado con id %d no existe", studentDTO.getId_tutor() );
        }
        return message;
    }

    public Student save( StudentDTO studentDTO, String action ) {
        Student student = new Student();
        if ( Objects.equals( action, "update" ) ) {
            student = studentRepository.findById( studentDTO.getId() ).orElse( null );
        }
        assert student != null;
        student.setName( studentDTO.getName() );
        student.setSurname( studentDTO.getSurname() );
        student.setGender( studentDTO.getId_gender() == 1 ? MASCULINO : FEMENINO );
        if ( !Objects.equals( student.getDni(), studentDTO.getDni() ) ) {
            student.setDni( studentDTO.getDni() );
        }
        student.setBirth_date( studentDTO.getBirthDate() );
        student.setAddress( studentDTO.getAddress() );
        student.setStatus( true );
        Tutor tutor = tutorRepository.findById( studentDTO.getId_tutor() ).orElse( null );
        student.setTutor( tutor );
        studentRepository.save( student );
        return student;
    }

    public List<StudentDTO> getList( List<Student> list ) {
        return list.stream()
                   .map( student -> builder().id( student.getId() )
                                             .complete_name( format( "%s %s", student.getName(), student.getSurname() ) )
                                             .dni( student.getDni() )
                                             .gender( student.getGender().toString() )
                                             .status( student.getStatus() ? HABILITADO : INHABILITADO )
                                             .build() )
                   .collect( toList() );
    }
}
