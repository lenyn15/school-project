package school.project.servicestudent.tutor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Integer> {

    @Query( value = "{ call filter_tutors(:filter) }",
            nativeQuery = true )
    List<Tutor> filterTutor( @Param( "filter" ) String filter );

    Optional<Tutor> findByDni( String dni );
}
