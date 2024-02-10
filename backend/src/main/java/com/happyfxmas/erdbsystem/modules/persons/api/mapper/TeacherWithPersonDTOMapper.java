package com.happyfxmas.erdbsystem.modules.persons.api.mapper;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.PersonDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.response.TeacherWithPersonDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Teacher;
import lombok.NonNull;

import java.util.List;

public class TeacherWithPersonDTOMapper {

    public static TeacherWithPersonDTO makeDTO(@NonNull Teacher teacher,
                                               @NonNull PersonDTO personDTO) {
        return TeacherWithPersonDTO.builder()
                .id(teacher.getId())
                .personDTO(personDTO)
                .build();
    }

    public static List<TeacherWithPersonDTO> makeDTOs(List<Teacher> teacherList) {
        return teacherList.stream()
                .map(teacher -> TeacherWithPersonDTOMapper.makeDTO(
                        teacher,
                        PersonDTOMapper.makeDTO(teacher.getPerson())
                ))
                .toList();
    }
}
