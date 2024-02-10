package com.happyfxmas.erdbsystem.modules.persons.api.dto.response;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StudentWithPersonDTO {
    private Long id;
    private PersonDTO personDTO;
}
