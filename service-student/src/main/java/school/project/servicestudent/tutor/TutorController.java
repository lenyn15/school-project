package school.project.servicestudent.tutor;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import school.project.servicestudent.exception.ApiRequestException;
import school.project.servicestudent.response.Response;

import javax.validation.Valid;
import java.util.List;

import static java.lang.String.*;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static school.project.servicestudent.enums.Status.HABILITADO;
import static school.project.servicestudent.enums.Status.INHABILITADO;
import static school.project.servicestudent.response.Response.*;
import static school.project.servicestudent.validation.Message.formatMessage;

@RestController
@AllArgsConstructor
@CrossOrigin( origins = "*" )
@RequestMapping( path = "/tutor",
                 produces = "application/json" )
public class TutorController {

    private final TutorService tutorService;

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ok( builder().dateTime( now() )
                            .data( of( "tutors", tutorService.showAll() ) )
                            .message( "Todos los tutores cargados correctamente" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/status/{status}" )
    public ResponseEntity<Response> searchByStatus( @PathVariable( "status" ) Long status ) {
        return ok( builder().dateTime( now() )
                            .data( of( "tutors", tutorService.filterByStatus( status ) ) )
                            .message( status == 1 ?
                                      "Tutores filtrados correctamente por el estado " + HABILITADO :
                                      "Tutores filtrados correctamente por el estado " + INHABILITADO )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/filter/{filter}" )
    public ResponseEntity<Response> search( @PathVariable( "filter" ) String word ) {
        List<TutorDTO> tutorList = tutorService.searchTutors( word );
        return ok( builder().dateTime( now() )
                            .data( of( "tutors", tutorList ) )
                            .message( tutorList.isEmpty() ?
                                      "No se pudo realizar la busqueda" :
                                      "Tutores filtrados correctamente" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/find/{dni}" )
    public ResponseEntity<Response> filterByDni( @PathVariable( "dni" ) String dniTutor ) {
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutorService.searchByDni( dniTutor ) ) )
                            .message( "Tutor encontrado" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/{id}" )
    public ResponseEntity<Response> getTutor( @PathVariable( "id" ) Long idTutor ) {
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutorService.getOne( idTutor ) ) )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @PostMapping
    public ResponseEntity<Response> addOne( @Valid @RequestBody TutorDTO tutor,
                                            BindingResult result ) {
        if ( result.hasErrors() ) {
            throw new ApiRequestException( formatMessage( result ) );
        }
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutorService.add( tutor ) ) )
                            .message( format( "Tutor %s %s creado correctamente", tutor.getName(), tutor.getSurname() ) )
                            .status( CREATED )
                            .statusCode( CREATED.value() )
                            .build() );
    }

    @PutMapping
    public ResponseEntity<Response> updateOne( @Valid @RequestBody TutorDTO tutorDTO,
                                               BindingResult result ) {
        if ( result.hasErrors() ) {
            throw new ApiRequestException( formatMessage( result ) );
        }
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutorService.update( tutorDTO ) ) )
                            .message( "Datos actualizados correctamente" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @DeleteMapping( path = "/disable/{id}" )
    public ResponseEntity<Response> disableOne( @PathVariable( "id" ) Long idTutor ) {
        TutorDTO tutor = tutorService.getOne( idTutor );
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutorService.disable( idTutor ) ) )
                            .message( format( "Tutor %s ha sido inhabilitado", tutor.getComplete_name() ) )
                            .status( GONE )
                            .statusCode( GONE.value() )
                            .build() );
    }

    @DeleteMapping( path = "/enable/{id}" )
    public ResponseEntity<Response> enableOne( @PathVariable( "id" ) Long idTutor ) {
        TutorDTO tutor = tutorService.getOne( idTutor );
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutorService.enable( idTutor ) ) )
                            .message( format( "Tutor %s ha sido habilitado", tutor.getComplete_name() ) )
                            .status( GONE )
                            .statusCode( GONE.value() )
                            .build() );
    }
}
