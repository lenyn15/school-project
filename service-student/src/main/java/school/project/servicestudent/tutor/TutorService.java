package school.project.servicestudent.tutor;

import org.springframework.stereotype.Service;
import school.project.servicestudent.exception.ApiRequestException;
import school.project.servicestudent.student.StudentDTO;
import school.project.servicestudent.validation.TutorMethods;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static school.project.servicestudent.enums.Status.HABILITADO;
import static school.project.servicestudent.enums.Status.INHABILITADO;
import static school.project.servicestudent.tutor.TutorDTO.*;

@Service
public record TutorService(
        TutorRepository tutorRepository,
        TutorMethods methods
) {

    public List<TutorDTO> showAll() {
        List<Tutor> tutorList = tutorRepository.findAll();
        if ( tutorList.isEmpty() ) {
            throw new ApiRequestException( "No hay apoderados registrados" );
        }
        return methods.getList( tutorList );
    }

    public List<TutorDTO> searchTutors( String filter ) {
        List<Tutor> tutorList = tutorRepository.filterTutor( filter );
        return methods.getList( tutorList );
    }

    public List<TutorDTO> filterByStatus( Long status ) {
        Boolean tutorStatus = status == 1;
        List<Tutor> tutorList = tutorRepository.findByStatus( tutorStatus );
        return methods.getList( tutorList );
    }

    public TutorDTO searchByDni( String dni ) {
        Boolean existDni = tutorRepository.existDni( dni );
        if ( !existDni ) {
            throw new ApiRequestException( format( "El apoderado con dni %s no esta registrado", dni ) );
        }
        Tutor tutorDB = tutorRepository.findByDni( dni );
        if ( !tutorDB.getStatus() ) {
            throw new ApiRequestException( format( "El apoderado %s %s se encuentra inhabilitado", tutorDB.getName(), tutorDB.getSurname() ) );
        }
        return builder().id( tutorDB.getId() ).name( tutorDB.getName() ).surname( tutorDB.getSurname() ).build();
    }

    public TutorDTO getOne( Long id ) {
        Tutor tutorDB = tutorRepository.findById( id ).orElse( null );
        if ( tutorDB == null ) {
            throw new ApiRequestException( format( "El apoderado con id %d no existe", id ) );
        }
        if ( !tutorDB.getStatus() ) {
            throw new ApiRequestException( format( "El apoderado %s %s se encuentra inhabilitado", tutorDB.getName(), tutorDB.getSurname() ) );
        }
        return builder().complete_name( format( "%s %s", tutorDB.getName(), tutorDB.getSurname() ) )
                        .gender( tutorDB.getGender().toString() )
                        .dni( tutorDB.getDni() )
                        .phone( tutorDB.getPhone() )
                        .email( tutorDB.getEmail() )
                        .occupation( tutorDB.getOccupation() )
                        .students( tutorDB.getStudents()
                                          .stream()
                                          .map( student -> StudentDTO.builder()
                                                                     .complete_name( format( "%s %s", student.getName(), student.getSurname() ) )
                                                                     .status( student.getStatus() ? HABILITADO :
                                                                              INHABILITADO )
                                                                     .gender( student.getGender().toString() )
                                                                     .dni( student.getDni() )
                                                                     .build() )
                                          .collect( toList() ) )
                        .build();
    }

    public Tutor add( TutorDTO tutorDTO ) {
        String message = methods.validate( tutorDTO, "" );
        if ( !Objects.equals( message, "" ) ) {
            throw new ApiRequestException( message );
        }
        return methods.save( tutorDTO, "" );
    }

    public Tutor update( TutorDTO tutorDTO ) {
        String message = methods.validate( tutorDTO, "update" );
        if ( !Objects.equals( message, "" ) ) {
            throw new ApiRequestException( message );
        }
        return methods.save( tutorDTO, "update" );
    }

    public Tutor disable( Long id ) {
        Tutor tutorDB = tutorRepository.findById( id ).orElse( null );
        if ( tutorDB == null ) {
            throw new ApiRequestException( format( "El apoderado con id %d no existe", id ) );
        }
        if ( !tutorDB.getStatus() ) {
            throw new ApiRequestException( format( "Apoderado %s %s, ya está inhabilitado", tutorDB.getName(), tutorDB.getSurname() ) );
        }
        tutorDB.setStatus( false );
        tutorRepository.save( tutorDB );
        return tutorDB;
    }

    public Tutor enable( Long id ) {
        Tutor tutorDB = tutorRepository.findById( id ).orElse( null );
        if ( tutorDB == null ) {
            throw new ApiRequestException( format( "El apoderado con id %d no existe", id ) );
        }
        if ( tutorDB.getStatus() ) {
            throw new ApiRequestException( format( "Apoderado %s %s, ya está habilitado", tutorDB.getName(), tutorDB.getSurname() ) );
        }
        tutorDB.setStatus( true );
        tutorRepository.save( tutorDB );
        return tutorDB;
    }
}
