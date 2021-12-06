package school.project.servicestudent.student;

import org.springframework.stereotype.Service;
import school.project.servicestudent.exception.ApiRequestException;
import school.project.servicestudent.validation.StudentMethods;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static school.project.servicestudent.student.StudentDTO.*;

@Service
public record StudentService(
        StudentRepository studentRepository,
        StudentMethods methods
) {

    public List<StudentDTO> showAll() {
        List<Student> studentList = studentRepository.findAll();
        if ( studentList.isEmpty() ) {
            throw new ApiRequestException( "No hay alumnos registrados" );
        }
        return methods.getList( studentList );
    }

    public List<StudentDTO> searchStudents( String filter ) {
        List<Student> studentList = studentRepository.filterStudent( filter );
        return methods.getList( studentList );
    }

    public List<StudentDTO> filterByStatus( Long status ) {
        Boolean studentStatus = status == 1;
        List<Student> studentList = studentRepository.findByStatus( studentStatus );
        return methods.getList( studentList );
    }

    public StudentDTO searchByDni( String dni ) {
        Boolean existDni = studentRepository.existDni( dni );
        if ( !existDni ) {
            throw new ApiRequestException( format( "El estudiante con dni %s no esta registrado", dni ) );
        }
        Student studentDB = studentRepository.findByDni( dni );
        if ( !studentDB.getStatus() ) {
            throw new ApiRequestException( format( "El Estudiante %s %s se encuentra inhabilitado", studentDB.getName(), studentDB.getSurname() ) );
        }
        return builder().id( studentDB.getId() ).name( studentDB.getName() ).surname( studentDB.getSurname() ).build();
    }

    public StudentDTO getOne( Long id ) {
        Student studentDB = studentRepository.findById( id ).orElse( null );
        if ( studentDB == null ) {
            throw new ApiRequestException( format( "El estudiante con id %d no existe", id ) );
        }
        if ( !studentDB.getStatus() ) {
            throw new ApiRequestException( format( "El Estudiante %s %s se encuentra inhabilitado", studentDB.getName(), studentDB.getSurname() ) );
        }
        return builder().complete_name( format( "%s %s", studentDB.getName(), studentDB.getSurname() ) )
                        .gender( studentDB.getGender().toString() )
                        .dni( studentDB.getDni() )
                        .birth_date( studentDB.getBirth_date().format( ofPattern( "dd/MM/yyyy" ) ) )
                        .address( studentDB.getAddress() )
                        .build();
    }

    public Student add( StudentDTO studentDTO ) {
        String message = methods.validate( studentDTO, "" );
        if ( !Objects.equals( message, "" ) ) {
            throw new ApiRequestException( message );
        }
        return methods.save( studentDTO, "" );
    }

    public Student update( StudentDTO studentDTO ) {
        String message = methods.validate( studentDTO, "update" );
        if ( !Objects.equals( message, "" ) ) {
            throw new ApiRequestException( message );
        }
        return methods.save( studentDTO, "update" );
    }

    public Student disable( Long id ) {
        Student studentDB = studentRepository.findById( id ).orElse( null );
        assert studentDB != null;
        if ( !studentDB.getStatus() ) {
            throw new ApiRequestException( format( "Estudiante %s %s, ya está inhabilitado", studentDB.getName(), studentDB.getSurname() ) );
        }
        studentDB.setStatus( false );
        studentRepository.save( studentDB );
        return studentDB;
    }

    public Student enable( Long id ) {
        Student studentDB = studentRepository.findById( id ).orElse( null );
        assert studentDB != null;
        if ( studentDB.getStatus() ) {
            throw new ApiRequestException( format( "Estudiante %s %s, ya está habilitado", studentDB.getName(), studentDB.getSurname() ) );
        }
        studentDB.setStatus( true );
        studentRepository.save( studentDB );
        return studentDB;
    }
}
