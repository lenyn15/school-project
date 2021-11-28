package school.project.servicestudent.tutor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    @Query( value = "{ call filter_tutors(:filter) }",
            nativeQuery = true )
    List<Tutor> filterTutor( @Param( "filter" ) String filter );

    List<Tutor> findByStatus( Boolean status );

    @Query( "select "
            + "case when count(t) > 0 then "
            + "true else "
            + "false end "
            + "from Tutor t where t.dni = :dni" )
    Boolean existDni( @Param( "dni" ) String dni );

    @Query( "select "
            + "case when count(t) > 0 then "
            + "true else "
            + "false end "
            + "from Tutor t where t.email = :email" )
    Boolean existEmail( @Param( "email" ) String email );

    Tutor findByDni( String dni );

    Optional<Tutor> findByNameAndSurname( String name,
                                          String surname );
}
