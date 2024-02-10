package com.happyfxmas.erdbsystem.modules.persons.api.mapper;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.PersonDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.PositionDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.TeacherDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Teacher;
import lombok.NonNull;

public class TeacherDTOMapper {

    public static TeacherDTO makeDTO(@NonNull Teacher teacher,
                                     @NonNull PersonDTO personDTO,
                                     @NonNull PositionDTO positionDTO) {
        return TeacherDTO.builder()
                .id(teacher.getId())
                .personDTO(personDTO)
                .positionDTO(positionDTO)
                .build();
    }
}
