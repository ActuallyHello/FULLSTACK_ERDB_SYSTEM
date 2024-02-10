package com.happyfxmas.erdbsystem.modules.persons.api.mapper;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.PersonDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.response.StudentWithPersonDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Student;
import lombok.NonNull;

import java.util.List;

public class StudentWithPersonDTOMapper {
    public static StudentWithPersonDTO makeDTO(@NonNull Student student,
                                               @NonNull PersonDTO personDTO) {
        return StudentWithPersonDTO.builder()
                .id(student.getId())
                .personDTO(personDTO)
                .build();
    }

    public static List<StudentWithPersonDTO> makeDTOs(List<Student> studentList) {
        return studentList.stream()
                .map(student -> StudentWithPersonDTOMapper.makeDTO(
                        student,
                        PersonDTOMapper.makeDTO(student.getPerson()))
                )
                .toList();
    }
}
