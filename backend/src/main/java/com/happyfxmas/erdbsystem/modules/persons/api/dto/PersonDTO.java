package com.happyfxmas.erdbsystem.modules.persons.api.dto;

import com.happyfxmas.erdbsystem.modules.persons.store.models.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private PersonType personType;
}
