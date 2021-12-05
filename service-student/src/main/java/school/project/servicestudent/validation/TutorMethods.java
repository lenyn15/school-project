package school.project.servicestudent.validation;

import org.springframework.stereotype.Component;
import school.project.servicestudent.tutor.Tutor;
import school.project.servicestudent.tutor.TutorDTO;
import school.project.servicestudent.tutor.TutorRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static school.project.servicestudent.enums.Gender.FEMENINO;
import static school.project.servicestudent.enums.Gender.MASCULINO;
import static school.project.servicestudent.enums.Status.HABILITADO;
import static school.project.servicestudent.enums.Status.INHABILITADO;
import static school.project.servicestudent.tutor.TutorDTO.*;

@Component
public record TutorMethods( TutorRepository tutorRepository ) {

    public String validate( TutorDTO tutorDTO, String action ) {
        String message = "";
        Tutor tutorDB = new Tutor();
        if ( Objects.equals( action, "update" ) ) {
            tutorDB = tutorRepository.findById( tutorDTO.getId() ).orElse( null );
            if ( tutorDB == null ) {
                return format( "El apoderado con id %d no existe", tutorDTO.getId() );
            }
        }

        // Valida el nombre y apellido del apoderado
        if ( !Objects.equals( tutorDB.getName(), tutorDTO.getName() ) &&
                !Objects.equals( tutorDB.getSurname(), tutorDTO.getSurname() ) ) {
            Optional<Tutor> complete_nameDB = tutorRepository.findByNameAndSurname( tutorDTO.getName(), tutorDTO.getSurname() );
            if ( complete_nameDB.isPresent() ) {
                return format( "Apoderado con nombre %s %s ya existe", tutorDTO.getName(), tutorDTO.getSurname() );
            }
            if ( tutorDTO.getName().isEmpty() || tutorDTO.getName() == null ) {
                return "El campo nombre del apoderado esta vacío";
            }
            if ( tutorDTO.getSurname().isEmpty() || tutorDTO.getSurname() == null ) {
                return "El campo apellido del apoderado esta vacío";
            }
        }

        // Valida el DNI del apoderado
        if ( !Objects.equals( tutorDB.getDni(), tutorDTO.getDni() ) ) {
            Boolean existDni = tutorRepository.existDni( tutorDTO.getDni() );
            if ( existDni ) {
                return "El DNI ingresado ya existe, ingrese otro";
            }
            if ( tutorDTO.getDni().isEmpty() || tutorDTO.getDni() == null ) {
                return "El campo DNI del apoderado esta vacío";
            }
            if ( tutorDTO.getDni().length() != 8 ) {
                return "El campo DNI debe ser de tamaño 8";
            }
        }

        // Valida el telefono del apoderado
        if ( tutorDTO.getPhone().isEmpty() || tutorDTO.getPhone() == null ) {
            return "El campo telefono del apoderado esta vacío";
        }
        if ( tutorDTO.getPhone().length() != 9 ) {
            return "El campo telefono debe ser de tamaño 8";
        }

        // Valida el email del apoderado
        if ( !Objects.equals( tutorDB.getEmail(), tutorDTO.getEmail() ) ) {
            Boolean existEmail = tutorRepository.existEmail( tutorDTO.getEmail() );
            if ( existEmail ) {
                return "El email ingresado ya existe, ingrese otro";
            }
            if ( tutorDTO.getEmail().isEmpty() || tutorDTO.getEmail() == null ) {
                return "El campo email del apoderado esta vacío";
            }
        }

        // Valida el campo trabajo del apoderado
        if ( tutorDTO.getOccupation().isEmpty() || tutorDTO.getOccupation() == null ) {
            return "El campo trabajo del apoderado esta vacío";
        }
        return message;
    }

    public Tutor save( TutorDTO tutorDTO, String action ) {
        Tutor tutor = new Tutor();
        if ( Objects.equals( action, "update" ) ) {
            tutor = tutorRepository.findById( tutorDTO.getId() ).orElse( null );
        }
        assert tutor != null;
        tutor.setName( tutorDTO.getName() );
        tutor.setSurname( tutorDTO.getSurname() );
        tutor.setGender( tutorDTO.getId_gender() == 1 ? MASCULINO : FEMENINO );
        if ( !Objects.equals( tutor.getDni(), tutorDTO.getDni() ) ) {
            tutor.setDni( tutorDTO.getDni() );
        }
        tutor.setPhone( tutorDTO.getPhone() );
        if ( !Objects.equals( tutor.getEmail(), tutorDTO.getEmail() ) ) {
            tutor.setEmail( tutorDTO.getEmail() );
        }
        tutor.setOccupation( tutorDTO.getOccupation() );
        tutor.setStatus( true );
        tutorRepository.save( tutor );
        return tutor;
    }

    public List<TutorDTO> getList( List<Tutor> list ) {
        return list.stream()
                   .map( tutor -> builder().id( tutor.getId() )
                                           .complete_name( format( "%s %s", tutor.getName(), tutor.getSurname() ) )
                                           .dni( tutor.getDni() )
                                           .gender( tutor.getGender().toString() )
                                           .status( tutor.getStatus() ? HABILITADO : INHABILITADO )
                                           .build() )
                   .collect( toList() );
    }
}
