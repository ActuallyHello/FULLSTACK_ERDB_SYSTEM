package com.happyfxmas.erdbsystem.modules.persons.api.mapper;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.GroupDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.PersonDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.StudentDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Student;
import lombok.NonNull;

public class StudentDTOMapper {

    public static StudentDTO makeDTO(@NonNull Student student,
                                     @NonNull GroupDTO groupDTO,
                                     @NonNull PersonDTO personDTO) {
        return StudentDTO.builder()
                .id(student.getId())
                .groupDTO(groupDTO)
                .personDTO(personDTO)
                .build();
    }
}
