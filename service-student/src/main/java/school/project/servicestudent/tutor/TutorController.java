package school.project.servicestudent.tutor;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import school.project.servicestudent.exception.ApiRequestException;
import school.project.servicestudent.response.Response;

import javax.validation.Valid;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;
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
                            .message( "Todos los tutores cargados" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @GetMapping( path = "/filter/{filter}" )
    public ResponseEntity<Response> search( @PathVariable( "filter" ) String word ) {
        List<Tutor> tutorList = tutorService.searchTutors( word );
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
    public ResponseEntity<Response> getTutor( @PathVariable( "id" ) int idTutor ) {
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutorService.getOne( idTutor ) ) )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @PostMapping
    public ResponseEntity<Response> addOne( @Valid @RequestBody Tutor tutor,
                                            BindingResult result ) {
        if ( result.hasErrors() ) {
            throw new ApiRequestException( formatMessage( result ) );
        }
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutorService.add( tutor ) ) )
                            .message( "Tutor creado correctamente" )
                            .status( CREATED )
                            .statusCode( CREATED.value() )
                            .build() );
    }

    @PutMapping( path = "/{id}" )
    public ResponseEntity<Response> updateOne( @PathVariable( "id" ) int idTutor,
                                               @Valid @RequestBody Tutor tutor,
                                               BindingResult result ) {
        if ( result.hasErrors() ) {
            throw new ApiRequestException( formatMessage( result ) );
        }
        return ok( builder().dateTime( now() )
                            .data( of( "tutor", tutorService.update( idTutor, tutor ) ) )
                            .message( "Tutor actualizado correctamente" )
                            .status( OK )
                            .statusCode( OK.value() )
                            .build() );
    }

    @DeleteMapping( path = "/{id}" )
    public ResponseEntity<Response> deleteOne( @PathVariable( "id" ) int idTutor ) {
        tutorService.destroy( idTutor );
        return ok( builder().dateTime( now() )
                            .message( "Tutor con id "
                                              + idTutor
                                              + ", eliminado" )
                            .status( GONE )
                            .statusCode( GONE.value() )
                            .build() );
    }
}
