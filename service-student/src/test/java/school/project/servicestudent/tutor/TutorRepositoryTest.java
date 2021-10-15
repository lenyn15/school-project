package school.project.servicestudent.tutor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TutorRepositoryTest {

    @Autowired
    private TutorRepository underTest;

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

        Boolean expected = underTest.existDni( tutor.getDni() );
        assertThat( expected ).isTrue();
    }

    @Test
    void itShouldCheckIfDniDoesNotExist() {
        String dni = "32740375";
        Boolean expected = underTest.existDni( dni );
        assertThat( expected ).isFalse();
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

        Boolean expected = underTest.existEmail( tutor.getEmail() );
        assertThat( expected ).isTrue();
    }

    @Test
    void itShouldCheckIfEmailDoesNotExist() {
        String email = "liz@gmail.com";
        Boolean expected = underTest.existDni( email );
        assertThat( expected ).isFalse();
    }

    @Test
    void itShouldFound_WhenDniIsGiven() {
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

        Tutor tutorDB = underTest.findByDni( tutor.getDni() );
        assertThat( tutorDB ).isEqualTo( tutor );
    }
}