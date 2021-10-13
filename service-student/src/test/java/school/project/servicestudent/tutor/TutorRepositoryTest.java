package school.project.servicestudent.tutor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TutorRepositoryTest {

    @Autowired
    private TutorRepository underTest;

    @Test
    void itShouldFilterTutor() {
        List<Tutor> list = underTest.filterTutor( "gmail" );
        assertThat( list.size() ).isGreaterThan( 0 );
    }

    @Test
    void itShouldFindByDni() {
        Optional<Tutor> tutorDB = underTest.existDni( "74224461" );
        assertThat( tutorDB.isPresent() ).isTrue();
    }
}