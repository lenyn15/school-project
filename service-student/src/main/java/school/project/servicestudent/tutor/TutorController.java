package school.project.servicestudent.tutor;

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
@RequestMapping( path = "/tutor",
                 produces = "application/json" )
public record TutorController( TutorService tutorService ) {

    @GetMapping
    public ResponseEntity<Response> getAll() {
        Map<String, List<TutorDTO>> tutors = new java.util.HashMap<>();
        tutors.put( "tutors", tutorService.showAll() );
        return ok( builder().dateTime( now() )
                            .data( tutors )
                            .message( "Se cargaron correctamente todos los apoderados" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/status/{status}" )
    public ResponseEntity<Response> searchByStatus( @PathVariable( "status" ) Long status ) {
        String message = "Apoderados filtrados correctamente por el estado ";
        Map<String, List<TutorDTO>> tutors = new java.util.HashMap<>();
        tutors.put( "tutors", tutorService.filterByStatus( status ) );
        return ok( builder().dateTime( now() )
                            .data( tutors )
                            .message( status == 1 ? message + HABILITADO : message + INHABILITADO )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/filter/{filter}" )
    public ResponseEntity<Response> search( @PathVariable( "filter" ) String word ) {
        List<TutorDTO> tutors = tutorService.searchTutors( word );
        return ok( builder().dateTime( now() )
                            .data( of( "tutors", tutors ) )
                            .message( tutors.isEmpty() ? "No se pudo realizar la busqueda" :
                                      "Apoderados filtrados correctamente" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/find/{dni}" )
    public ResponseEntity<Response> filterByDni( @PathVariable( "dni" ) String dniTutor ) {
        Map<String, TutorDTO> tutor = new java.util.HashMap<>();
        tutor.put( "tutor", tutorService.searchByDni( dniTutor ) );
        return ok( builder().dateTime( now() )
                            .data( tutor )
                            .message( "Apoderado encontrado" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/{id}" )
    public ResponseEntity<Response> getTutor( @PathVariable( "id" ) Long idTutor ) {
        TutorDTO tutor = tutorService.getOne( idTutor );
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutor ) )
                            .message( format( "Informacion del apoderado %s, cargada correctamente", tutor.getComplete_name() ) )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @PostMapping
    public ResponseEntity<Response> addOne( @RequestBody TutorDTO tutor ) {
        Map<String, Tutor> newTutor = new java.util.HashMap<>();
        newTutor.put( "tutor", tutorService.add( tutor ) );
        return ok( builder().dateTime( now() )
                            .data( newTutor )
                            .message( format( "Apoderado %s %s creado correctamente", tutor.getName(), tutor.getSurname() ) )
                            .status( CREATED )
                            .statusCode( CREATED.value() )
                            .build() );
    }

    @PutMapping
    public ResponseEntity<Response> updateOne( @RequestBody TutorDTO tutorDTO ) {
        Tutor tutorUpdated = tutorService.update( tutorDTO );
        return ok( builder().dateTime( now() )
                            .data( of( "tutorUpdated", tutorUpdated ) )
                            .message( format( "Datos del apoderado %s %s actualizados correctamente", tutorUpdated.getName(), tutorUpdated.getSurname() ) )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @DeleteMapping( path = "/disable/{id}" )
    public ResponseEntity<Response> disableOne( @PathVariable( "id" ) Long idTutor ) {
        Tutor tutor = tutorService.disable( idTutor );
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutor ) )
                            .message( format( "Apoderado %s %s ha sido inhabilitado", tutor.getName(), tutor.getSurname() ) )
                            .status( GONE )
                            .statusCode( GONE.value() )
                            .build() );
    }

    @DeleteMapping( path = "/enable/{id}" )
    public ResponseEntity<Response> enableOne( @PathVariable( "id" ) Long idTutor ) {
        Tutor tutor = tutorService.enable( idTutor );
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutor ) )
                            .message( format( "Apoderado %s %s ha sido habilitado", tutor.getName(), tutor.getSurname() ) )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }
}
