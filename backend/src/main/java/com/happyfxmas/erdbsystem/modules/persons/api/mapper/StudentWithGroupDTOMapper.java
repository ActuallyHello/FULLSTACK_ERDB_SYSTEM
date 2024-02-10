package com.happyfxmas.erdbsystem.modules.persons.api.mapper;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.GroupDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.response.StudentWithGroupDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Student;
import lombok.NonNull;

public class StudentWithGroupDTOMapper {
    public static StudentWithGroupDTO makeDTO(@NonNull Student student,
                                              @NonNull GroupDTO groupDTO) {
        return StudentWithGroupDTO.builder()
                .id(student.getId())
                .groupDTO(groupDTO)
                .build();
    }
}
