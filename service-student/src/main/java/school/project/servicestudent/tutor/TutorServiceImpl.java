package school.project.servicestudent.tutor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.project.servicestudent.exception.ApiRequestException;
import school.project.servicestudent.validation.Methods;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;
    private final Methods methods;

    @Override
    public List<TutorDTO> showAll() {
        List<Tutor> tutorList = tutorRepository.findAll();
        if ( tutorList.isEmpty() ) {
            throw new ApiRequestException( "No hay apoderados registrados" );
        }
        return methods.getList( tutorList );
    }

    @Override
    public List<TutorDTO> searchTutors( String filter ) {
        List<Tutor> tutorList = tutorRepository.filterTutor( filter );
        return methods.getList( tutorList );
    }

    @Override
    public List<TutorDTO> filterByStatus( Long status ) {
        Boolean tutorStatus = status == 1;
        List<Tutor> tutorList = tutorRepository.findByStatus( tutorStatus );
        return methods.getList( tutorList );
    }

    @Override
    public TutorDTO searchByDni( String dni ) {
        Boolean existDni = tutorRepository.existDni( dni );
        if ( !existDni ) {
            throw new ApiRequestException( String.format( "El apoderado con dni %s no esta registrado", dni ) );
        }
        Tutor tutorDB = tutorRepository.findByDni( dni );
        if ( tutorDB.getStatus() ) {
            TutorDTO tutorDTO = new TutorDTO();
            tutorDTO.setName( tutorDB.getName() );
            tutorDTO.setSurname( tutorDB.getSurname() );

            return tutorDTO;
        } else {
            throw new ApiRequestException( String.format( "El Tutor %s %s se encuentra inhabilitado", tutorDB.getName(), tutorDB.getSurname() ) );
        }
    }

    @Override
    public TutorDTO getOne( Long id ) {
        Tutor tutorDB = tutorRepository.findById( id )
                                       .orElse( null );
        assert tutorDB != null;
        if ( tutorDB.getStatus() ) {
            TutorDTO tutorDTO = new TutorDTO();
            tutorDTO.setComplete_name( String.format( "%s %s", tutorDB.getName(), tutorDB.getSurname() ) );
            tutorDTO.setGender( tutorDB.getGender()
                                       .toString() );
            tutorDTO.setDni( tutorDB.getDni() );
            tutorDTO.setPhone( tutorDB.getPhone() );
            tutorDTO.setEmail( tutorDB.getEmail() );
            tutorDTO.setOccupation( tutorDB.getOccupation() );

            return tutorDTO;
        }

        if ( !tutorDB.getStatus() ) {
            throw new ApiRequestException( String.format( "El Tutor %s %s se encuentra inhabilitado", tutorDB.getName(), tutorDB.getSurname() ) );
        }
        return null;
    }

    @Override
    public Tutor add( TutorDTO tutorDTO ) {
        String message = methods.validate_tutor( tutorDTO, "" );
        if ( Objects.equals( message, "" ) ) {
            return methods.saveTutor( tutorDTO, "" );
        }
        return null;
    }

    @Override
    public Tutor update( TutorDTO tutorDTO ) {
        String message = methods.validate_tutor( tutorDTO, "update" );
        if ( Objects.equals( message, "" ) ) {
            return methods.saveTutor( tutorDTO, "update" );
        }
        return null;
    }

    @Transactional
    public Tutor disable( Long id ) {
        Tutor tutorDB = tutorRepository.findById( id )
                                       .orElse( null );
        assert tutorDB != null;
        if ( tutorDB.getStatus() ) {
            tutorDB.setStatus( false );
        } else {
            throw new ApiRequestException( String.format( "Tutor %s %s, ya está inhabilitado", tutorDB.getName(), tutorDB.getSurname() ) );
        }
        return tutorDB;
    }

    @Transactional
    public Tutor enable( Long id ) {
        Tutor tutorDB = tutorRepository.findById( id )
                                       .orElse( null );
        assert tutorDB != null;
        if ( !tutorDB.getStatus() ) {
            tutorDB.setStatus( true );
        } else {
            throw new ApiRequestException( String.format( "Tutor %s %s, ya está habilitado", tutorDB.getName(), tutorDB.getSurname() ) );
        }
        return tutorDB;
    }
}
