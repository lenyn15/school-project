package school.project.servicestudent.tutor;

import java.util.List;

public interface TutorService {
    List<Tutor> showAll();

    List<Tutor> searchTutors( String filter );

    Tutor searchByDni( String dni );

    Tutor getOne( int id );

    Tutor add( Tutor tutor );

    Tutor update( int id,
                  Tutor tutor );

    void destroy( int id );
}
