package school.project.servicestudent.tutor;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.GONE;

@RestController
@AllArgsConstructor
@CrossOrigin( origins = "*" )
@RequestMapping( path = "/tutor",
                 produces = "application/json" )
public class TutorController {

    private final TutorService tutorService;

    @GetMapping
    public ResponseEntity<List<Tutor>> getAll() {
        return ResponseEntity.ok( tutorService.showAll() );
    }

    @GetMapping( path = "/filter/{filter}" )
    public ResponseEntity<List<Tutor>> search( @PathVariable( "filter" ) String word ) {
        return ResponseEntity.ok( tutorService.searchTutors( word ) );
    }

    @GetMapping( path = "/find/{dni}" )
    public ResponseEntity<Tutor> filterByDni( @PathVariable( "dni" ) String dniTutor ) {
        return ResponseEntity.ok( tutorService.searchByDni( dniTutor ) );
    }

    @GetMapping( path = "/{id}" )
    public ResponseEntity<Tutor> getTutor( @PathVariable( "id" ) int idTutor ) {
        return ResponseEntity.ok( tutorService.getOne( idTutor ) );
    }

    @PostMapping
    public ResponseEntity<Tutor> addOne( @Valid @RequestBody Tutor tutor ) {
        return ResponseEntity
                .status( CREATED )
                .body( tutorService.add( tutor ) );
    }

    @PutMapping( path = "/{id}" )
    public ResponseEntity<Tutor> updateOne( @PathVariable( "id" ) int idTutor,
                                            @Valid @RequestBody Tutor tutor ) {
        return ResponseEntity.ok( tutorService.update( idTutor, tutor ) );
    }

    @DeleteMapping( path = "/{id}" )
    public ResponseEntity<?> deleteOne( @PathVariable( "id" ) int idTutor ) {
        tutorService.destroy( idTutor );
        return ResponseEntity
                .status( GONE )
                .body( null );
    }
}
