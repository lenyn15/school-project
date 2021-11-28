package school.project.servicestudent.tutor;

import java.util.List;

public interface TutorService {
    List<TutorDTO> showAll();

    List<TutorDTO> searchTutors( String filter );

    List<TutorDTO> filterByStatus( Long status );

    TutorDTO searchByDni( String dni );

    TutorDTO getOne( Long id );

    Tutor add( TutorDTO tutorDTO );

    Tutor update( TutorDTO tutorDTO );

    Tutor disable( Long id );

    Tutor enable( Long id );
}
