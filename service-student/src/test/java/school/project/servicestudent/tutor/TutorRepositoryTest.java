package school.project.servicestudent.tutor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TutorRepositoryTest {

    @Autowired
    private TutorRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfExistDni() {
        Tutor tutor = Tutor
                .builder()
                .name( "Lenyn Smith" )
                .surname( "Goicochea" )
                .gender( "Masculino" )
                .dni( "74224460" )
                .phone( "946025209" )
                .email( "lenyn@gmail.com" )
                .occupation( "Programador" )
                .build();
        underTest.save( tutor );

        Optional<Tutor> dni = underTest.existDni( tutor.getDni() );
        assertThat( dni.isPresent() ).isTrue();
    }

    @Test
    void itShouldCheckIfExistEmail() {
        Tutor tutor = Tutor
                .builder()
                .name( "Lenyn Smith" )
                .surname( "Goicochea" )
                .gender( "Masculino" )
                .dni( "74224460" )
                .phone( "946025209" )
                .email( "lenyn@gmail.com" )
                .occupation( "Programador" )
                .build();
        underTest.save( tutor );

        Optional<Tutor> email = underTest.existEmail( tutor.getEmail() );
        assertThat( email.isPresent() ).isTrue();
    }
}