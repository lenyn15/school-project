package school.project.servicestudent.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByDni( String dni );

    List<Student> findByStatus( Boolean status );

    Optional<Student> findByNameAndSurname( String name, String surname );

    @Query( value = "{ call filter_students(:filter) }",
            nativeQuery = true )
    List<Student> filterStudent( @Param( "filter" ) String filter );

    @Query( value = "select case when count(s) > 0 then true else false end from Student s where s.dni = :dni" )
    Boolean existDni( @Param( "dni" ) String dni );

}
