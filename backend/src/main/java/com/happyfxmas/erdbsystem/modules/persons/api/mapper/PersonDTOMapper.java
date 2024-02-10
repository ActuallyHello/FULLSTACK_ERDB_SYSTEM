package com.happyfxmas.erdbsystem.modules.persons.api.mapper;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.PersonDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.request.CreatePersonRequestDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.persons.store.models.enums.PersonType;
import lombok.NonNull;

import java.util.List;

public class PersonDTOMapper {
    public static PersonDTO makeDTO(@NonNull Person person) {
        return PersonDTO.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .middleName(person.getMiddleName())
                .personType(person.getPersonType())
                .build();
    }

    public static PersonDTO makeDTO(@NonNull CreatePersonRequestDTO createPersonRequestDTO) {
        return PersonDTO.builder()
                .firstName(createPersonRequestDTO.getFirstName())
                .lastName(createPersonRequestDTO.getLastName())
                .middleName(createPersonRequestDTO.getMiddleName())
                .personType(PersonType.fromString(createPersonRequestDTO.getPersonType()))
                .build();
    }

    public static Person fromDTO(@NonNull PersonDTO personDTO) {
        return Person.builder()
                .id(personDTO.getId())
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .middleName(personDTO.getMiddleName())
                .personType(personDTO.getPersonType())
                .build();
    }

    public static Person fromDTO(@NonNull CreatePersonRequestDTO createPersonRequestDTO) {
        return Person.builder()
                .firstName(createPersonRequestDTO.getFirstName())
                .lastName(createPersonRequestDTO.getLastName())
                .middleName(createPersonRequestDTO.getMiddleName())
                .personType(PersonType.fromString(createPersonRequestDTO.getPersonType()))
                .build();
    }

    public static List<PersonDTO> makeDTOs(List<Person> personList) {
        return personList.stream()
                .map(PersonDTOMapper::makeDTO)
                .toList();
    }
}
