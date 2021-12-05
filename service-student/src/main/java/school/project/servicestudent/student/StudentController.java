package school.project.servicestudent.student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.project.servicestudent.response.Response;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static school.project.servicestudent.enums.Status.HABILITADO;
import static school.project.servicestudent.enums.Status.INHABILITADO;
import static school.project.servicestudent.response.Response.*;

@RestController
@CrossOrigin( origins = "*" )
@RequestMapping( path = "/student",
                 produces = "application/json" )
public record StudentController( StudentService studentService ) {

    @GetMapping
    public ResponseEntity<Response> getAll() {
        Map<String, List<StudentDTO>> students = new java.util.HashMap<>();
        students.put( "students", studentService.showAll() );
        return ok( builder().dateTime( now() )
                            .data( students )
                            .message( "Se cargaron correctamente todos los estudiantes" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/status/{status}" )
    public ResponseEntity<Response> searchByStatus( @PathVariable( "status" ) Long status ) {
        String message = "Estudiante filtrados correctamente por el estado ";
        Map<String, List<StudentDTO>> students = new java.util.HashMap<>();
        students.put( "students", studentService.filterByStatus( status ) );
        return ok( builder().dateTime( now() )
                            .data( students )
                            .message( status == 1 ? message + HABILITADO : message + INHABILITADO )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/filter/{filter}" )
    public ResponseEntity<Response> search( @PathVariable( "filter" ) String word ) {
        List<StudentDTO> students = studentService.searchStudents( word );
        return ok( builder().dateTime( now() )
                            .data( of( "students", students ) )
                            .message( students.isEmpty() ? "No se pudo realizar la busqueda" :
                                      "Estudiantes filtrados correctamente" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/find/{dni}" )
    public ResponseEntity<Response> filterByDni( @PathVariable( "dni" ) String dniStudent ) {
        Map<String, StudentDTO> student = new java.util.HashMap<>();
        student.put( "student", studentService.searchByDni( dniStudent ) );
        return ok( builder().dateTime( now() )
                            .data( student )
                            .message( "Estudiante encontrado" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/{id}" )
    public ResponseEntity<Response> getStudent( @PathVariable( "id" ) Long idStudent ) {
        StudentDTO student = studentService.getOne( idStudent );
        return ok( builder().dateTime( now() )
                            .data( of( "student", student ) )
                            .message( format( "Informacion del estudiante %s, cargada correctamente", student.getComplete_name() ) )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @PostMapping
    public ResponseEntity<Response> addOne( @RequestBody StudentDTO studentDTO ) {
        Map<String, Student> newStudent = new java.util.HashMap<>();
        newStudent.put( "student", studentService.add( studentDTO ) );
        return ok( builder().dateTime( now() )
                            .data( newStudent )
                            .message( format( "Estudiante %s %s creado correctamente", studentDTO.getName(), studentDTO.getSurname() ) )
                            .status( CREATED )
                            .statusCode( CREATED.value() )
                            .build() );
    }

    @PutMapping
    public ResponseEntity<Response> updateOne( @RequestBody StudentDTO studentDTO ) {
        Student studentUpdated = studentService.update( studentDTO );
        return ok( builder().dateTime( now() )
                            .data( of( "studentUpdated", studentUpdated ) )
                            .message( format( "Datos del estudiante %s %s actualizados correctamente", studentUpdated.getName(), studentUpdated.getSurname() ) )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @DeleteMapping( path = "/disable/{id}" )
    public ResponseEntity<Response> disableOne( @PathVariable( "id" ) Long idStudent ) {
        Student studentDisabled = studentService.disable( idStudent );
        return ok( builder().dateTime( now() )
                            .data( of( "studentDisabled", studentDisabled ) )
                            .message( format( "Estudiante %s %s ha sido inhabilitado", studentDisabled.getName(), studentDisabled.getSurname() ) )
                            .status( GONE )
                            .statusCode( GONE.value() )
                            .build() );
    }

    @DeleteMapping( path = "/enable/{id}" )
    public ResponseEntity<Response> enableOne( @PathVariable( "id" ) Long idStudent ) {
        Student studentEnabled = studentService.enable( idStudent );
        return ok( builder().dateTime( now() )
                            .data( of( "studentEnabled", studentEnabled ) )
                            .message( format( "Estudiante %s %s ha sido habilitado", studentEnabled.getName(), studentEnabled.getSurname() ) )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }
}
