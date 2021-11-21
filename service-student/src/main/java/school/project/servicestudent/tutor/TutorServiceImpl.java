package school.project.servicestudent.tutor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.project.servicestudent.exception.ApiRequestException;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;

    @Override
    public List<Tutor> showAll() {
        List<Tutor> tutorList = tutorRepository.findAll();
        if ( tutorList.isEmpty() ) {
            throw new ApiRequestException( "No hay apoderados registrados" );
        }
        return tutorList;
    }

    @Override
    public List<Tutor> searchTutors( String filter ) {
        return tutorRepository.filterTutor( filter );
    }

    @Override
    public Tutor searchByDni( String dni ) {
        Boolean existDni = tutorRepository.existDni( dni );
        if ( !existDni ) {
            throw new ApiRequestException( "El apoderado con dni "
                                                   + dni
                                                   + " no esta registrado" );
        }
        return tutorRepository.findByDni( dni );
    }

    @Override
    public Tutor getOne( int id ) {
        return tutorRepository.findById( id )
                              .orElse( null );
    }

    @Override
    public Tutor add( Tutor tutor ) {
        Boolean existDni = tutorRepository.existDni( tutor.getDni() );
        if ( existDni ) {
            throw new ApiRequestException( "El DNI ingresado ya existe, ingrese otro" );
        }

        Boolean existEmail = tutorRepository.existEmail( tutor.getEmail() );
        if ( existEmail ) {
            throw new ApiRequestException( "El email ingresado ya existe, ingrese otro" );
        }
        return tutorRepository.save( tutor );
    }

    @Override
    public Tutor update( int id,
                         Tutor tutor ) {
        Tutor tutorDB = getOne( id );
        if ( !Objects.equals( tutorDB.getDni(), tutor.getDni() ) ) {
            Boolean existDni = tutorRepository.existDni( tutor.getDni() );
            if ( existDni ) {
                throw new ApiRequestException( "El DNI ingresado ya existe, ingrese otro" );
            }

            tutorDB.setDni( tutor.getDni() );
        }

        if ( !Objects.equals( tutorDB.getEmail(), tutor.getEmail() ) ) {
            Boolean existEmail = tutorRepository.existEmail( tutor.getEmail() );
            if ( existEmail ) {
                throw new ApiRequestException( "El email ingresado ya existe, ingrese otro" );
            }

            tutorDB.setEmail( tutor.getEmail() );
        }

        tutorDB.setName( tutor.getName() );
        tutorDB.setSurname( tutor.getSurname() );
        tutorDB.setGender( tutor.getGender() );
        tutorDB.setPhone( tutor.getPhone() );
        tutorDB.setOccupation( tutor.getOccupation() );
        return tutorRepository.save( tutorDB );
    }

    @Override
    public void destroy( int id ) {
        tutorRepository.deleteById( id );
    }
}
