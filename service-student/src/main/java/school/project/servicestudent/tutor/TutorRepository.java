package school.project.servicestudent.tutor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Integer> {

    @Query( value = "{ call filter_tutors(:filter) }",
            nativeQuery = true )
    List<Tutor> filterTutor( @Param( "filter" ) String filter );

    @Query( "select t from Tutor t where t.dni = :dni" )
    Boolean existDni( @Param( "dni" ) String dni );

    @Query( "select t from Tutor t where t.email = ?1" )
    Boolean existEmail( String email );

    Tutor findByDni( String dni );
}
