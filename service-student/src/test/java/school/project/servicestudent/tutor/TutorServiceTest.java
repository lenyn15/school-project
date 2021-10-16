package school.project.servicestudent.tutor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.project.servicestudent.exception.ApiRequestException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith( MockitoExtension.class )
class TutorServiceTest {

    private TutorServiceImpl underTest;

    @Mock
    private TutorRepository tutorRepository;

    @BeforeEach
    void setUp() {
        underTest = new TutorServiceImpl( tutorRepository );
        Tutor tutor1 = Tutor
                .builder()
                .id( 1 )
                .name( "Lenyn Smith" )
                .surname( "Goicochea" )
                .gender( "Masculino" )
                .dni( "74224460" )
                .phone( "946025209" )
                .email( "lenyn@gmail.com" )
                .occupation( "Programador" )
                .build();

        Tutor tutor2 = Tutor
                .builder()
                .id( 2 )
                .name( "Liz" )
                .surname( "Quezada Arevalp" )
                .gender( "Femenino" )
                .dni( "32740375" )
                .phone( "943012449" )
                .email( "liz@gmail.com" )
                .occupation( "Abogada" )
                .build();

        lenient()
                .when( tutorRepository.findByDni( tutor1.getDni() ) )
                .thenReturn( tutor1 );
        lenient()
                .when( tutorRepository.findByDni( tutor2.getDni() ) )
                .thenReturn( tutor2 );

        lenient()
                .when( tutorRepository.findById( 1 ) )
                .thenReturn( Optional.of( tutor1 ) );
        lenient()
                .when( tutorRepository.findById( 2 ) )
                .thenReturn( Optional.of( tutor2 ) );

        List<Tutor> list = new ArrayList<>();
        list.add( tutor1 );
        list.add( tutor2 );

        lenient()
                .when( tutorRepository.findAll() )
                .thenReturn( list );
    }

    @Test
    void itShouldShowAllTutors() {
        List<Tutor> list = underTest.showAll();
        Assertions
                .assertThat( list.size() )
                .isEqualTo( 2 );
    }

    @Test
    void itShouldNotShow_ifNotExistTutors() {
        given( tutorRepository.findAll() ).willReturn( Collections.emptyList() );

        assertThatThrownBy( () -> underTest.showAll() )
                .isInstanceOf( ApiRequestException.class )
                .hasMessageContaining( "No hay apoderados registrados" );
    }

    @Test
    void itShouldFound_IfDniExist() {
        given( tutorRepository.existDni( anyString() ) ).willReturn( true );
        Tutor tutorDB = underTest.getOne( 2 );

        Tutor tutor = underTest.searchByDni( tutorDB.getDni() );
        assertThat( tutor.getName() ).isEqualTo( "Liz" );
    }

    @Test
    void itShouldThrowIfDniDoesNotExist_WhenSearch() {
        given( tutorRepository.existDni( anyString() ) ).willReturn( false );

        String dni = "74224460";
        assertThatThrownBy( () -> underTest.searchByDni( dni ) )
                .isInstanceOf( ApiRequestException.class )
                .hasMessageContaining( "El apoderado con dni "
                                               + dni
                                               + " no esta registrado" );

        verify( tutorRepository, never() ).findByDni( anyString() );
    }

    @Test
    void itShouldGetOne() {
        Tutor tutor = underTest.getOne( 2 );
        assertThat( tutor ).isNotEqualTo( null );
    }

    @Test
    void itShouldCanAdd() {
        Tutor tutor = underTest.getOne( 1 );
        underTest.add( tutor );

        ArgumentCaptor<Tutor> tutorArgumentCaptor = ArgumentCaptor.forClass( Tutor.class );
        verify( tutorRepository ).save( tutorArgumentCaptor.capture() );

        Tutor tutorCaptured = tutorArgumentCaptor.getValue();
        assertThat( tutorCaptured ).isEqualTo( tutor );
    }

    @Test
    void whenAdd_ItShouldThrowIfDniExist() {
        given( tutorRepository.existDni( anyString() ) ).willReturn( true );

        Tutor tutor = underTest.getOne( 1 );
        assertThatThrownBy( () -> underTest.add( tutor ) )
                .isInstanceOf( ApiRequestException.class )
                .hasMessageContaining( "El DNI ingresado ya existe, ingrese otro" );

        verify( tutorRepository, never() ).save( any() );
    }

    @Test
    void whenAdd_ItShouldThrowIfEmailExist() {
        given( tutorRepository.existEmail( anyString() ) ).willReturn( true );

        Tutor tutor = underTest.getOne( 1 );
        assertThatThrownBy( () -> underTest.add( tutor ) )
                .isInstanceOf( ApiRequestException.class )
                .hasMessageContaining( "El email ingresado ya existe, ingrese otro" );

        verify( tutorRepository, never() ).save( any() );
    }

    @Test
    void itShouldUpdateOneTutor() {
        Tutor tutorDB = underTest.getOne( 1 );
        tutorDB.setName( "Smith" );
        underTest.update( 1, tutorDB );

        ArgumentCaptor<Tutor> tutorArgumentCaptor = ArgumentCaptor.forClass( Tutor.class );
        verify( tutorRepository ).save( tutorArgumentCaptor.capture() );

        Tutor tutorUpdated = tutorArgumentCaptor.getValue();

        assertThat( tutorUpdated.getName() ).isEqualTo( "Smith" );
    }

    @Test
    void itShouldThrowIfDniExist_WhenUpdate() {
        Tutor tutor = underTest.getOne( 2 );

        given( tutorRepository.existDni( anyString() ) ).willReturn( true );
        assertThatThrownBy( () -> underTest.update( 1, tutor ) )
                .isInstanceOf( ApiRequestException.class )
                .hasMessageContaining( "El DNI ingresado ya existe, ingrese otro" );
    }

    @Test
    void itShouldThrowIfEmailExist_WhenUpdate() {
        Tutor tutor = underTest.getOne( 2 );

        given( tutorRepository.existEmail( anyString() ) ).willReturn( true );
        assertThatThrownBy( () -> underTest.update( 1, tutor ) )
                .isInstanceOf( ApiRequestException.class )
                .hasMessageContaining( "El email ingresado ya existe, ingrese otro" );
    }

    @Test
    void itShouldDeleteOneTutor() {
        int id = 1;
        lenient()
                .when( tutorRepository.existsById( 1 ) )
                .thenReturn( true );

        underTest.destroy( id );
        verify( tutorRepository ).deleteById( any() );
    }
}