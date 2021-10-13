package school.project.servicestudent.tutor;

import java.util.List;

public interface TutorService {
    List<Tutor> listAll();

    List<Tutor> filterTutors( String filter );

    Tutor searchByDni( String dni );

    Tutor getTutor( int id );

    Tutor addTutor( Tutor tutor );

    Tutor updateTutor( int id,
                       Tutor tutor );

    void deleteTutor( int id );
}
