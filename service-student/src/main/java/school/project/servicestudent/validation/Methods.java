package school.project.servicestudent.validation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.project.servicestudent.tutor.Tutor;
import school.project.servicestudent.tutor.TutorDTO;
import school.project.servicestudent.tutor.TutorRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static school.project.servicestudent.enums.Gender.FEMENINO;
import static school.project.servicestudent.enums.Gender.MASCULINO;
import static school.project.servicestudent.enums.Status.HABILITADO;
import static school.project.servicestudent.enums.Status.INHABILITADO;

@AllArgsConstructor
@Component
public class Methods {

    private final TutorRepository tutorRepository;

    public String validate_tutor( TutorDTO tutorDTO,
                                  String action ) {
        String message = "";
        Tutor tutorDB = new Tutor();
        if ( Objects.equals( action, "update" ) ) {
            tutorDB = tutorRepository.findById( tutorDTO.getId() )
                                     .orElse( null );
        }
        assert tutorDB != null;

        if ( !Objects.equals( tutorDB.getName(), tutorDTO.getName() ) && !Objects.equals( tutorDB.getSurname(), tutorDTO.getSurname() ) ) {
            Optional<Tutor> complete_nameDB = tutorRepository.findByNameAndSurname( tutorDTO.getName(), tutorDTO.getSurname() );
            if ( complete_nameDB.isPresent() ) {
                return String.format( "Tutor con nombre %s %s ya existe", tutorDTO.getName(), tutorDTO.getSurname() );
            }
        }

        if ( !Objects.equals( tutorDB.getDni(), tutorDTO.getDni() ) ) {
            Boolean existDni = tutorRepository.existDni( tutorDTO.getDni() );
            if ( existDni ) {
                return "El DNI ingresado ya existe, ingrese otro";
            }
        }

        if ( !Objects.equals( tutorDB.getEmail(), tutorDTO.getEmail() ) ) {
            Boolean existEmail = tutorRepository.existEmail( tutorDTO.getEmail() );
            if ( existEmail ) {
                return "El email ingresado ya existe, ingrese otro";
            }
        }
        return message;
    }

    public Tutor saveTutor( TutorDTO tutorDTO,
                            String action ) {
        Tutor tutor = new Tutor();
        if ( Objects.equals( action, "update" ) ) {
            tutor = tutorRepository.findById( tutorDTO.getId() )
                                   .orElse( null );
        }
        assert tutor != null;
        tutor.setName( tutorDTO.getName() );
        tutor.setSurname( tutorDTO.getSurname() );
        tutor.setGender( tutorDTO.getId_gender() == 1 ?
                         MASCULINO :
                         FEMENINO );
        if ( !Objects.equals( tutor.getDni(), tutorDTO.getDni() ) ) {
            tutor.setDni( tutorDTO.getDni() );
        }
        tutor.setPhone( tutorDTO.getPhone() );
        if ( !Objects.equals( tutor.getEmail(), tutorDTO.getEmail() ) ) {
            tutor.setEmail( tutorDTO.getEmail() );
        }
        tutor.setOccupation( tutorDTO.getOccupation() );
        tutorRepository.save( tutor );
        return tutor;
    }

    public List<TutorDTO> getList( List<Tutor> list ) {
        return list.stream()
                   .map( tutor -> {
                       TutorDTO tutorDTO = new TutorDTO();
                       tutorDTO.setComplete_name( String.format( "%s %s", tutor.getName(), tutor.getSurname() ) );
                       tutorDTO.setDni( tutor.getDni() );
                       tutorDTO.setGender( tutor.getGender()
                                                .toString() );
                       tutorDTO.setStatus( tutor.getStatus() ?
                                           HABILITADO :
                                           INHABILITADO );
                       return tutorDTO;
                   } )
                   .collect( Collectors.toList() );
    }
}
