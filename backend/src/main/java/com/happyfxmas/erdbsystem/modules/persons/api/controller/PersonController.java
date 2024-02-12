package com.happyfxmas.erdbsystem.modules.persons.api.controller;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.PersonDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.request.CreatePersonRequestDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.service.PersonService;
import com.happyfxmas.erdbsystem.modules.persons.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${APP_REST_API_PREFIX}/${APP_REST_API_VERSION}/persons")
@CrossOrigin(origins = "${APP_FRONT_URL}")
public class PersonController {

    private final PersonService personService;
    private final UserService userService;

    private static final String BY_ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getPersons() {
        var personList = personService.getAll();
        var personDTOList = PersonDTOMapper.makeDTOs(personList);
        return ResponseEntity.ok(personDTOList);
    }

    @GetMapping("/not-used")
    public ResponseEntity<List<PersonDTO>> getPersonsNotUsed() {
        var personList = personService.getAllNotUsed();
        var personDTOList = PersonDTOMapper.makeDTOs(personList);
        return ResponseEntity.ok(personDTOList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        var person = personService.getById(id);
        var personDTO = PersonDTOMapper.makeDTO(person);
        return ResponseEntity.ok(personDTO);
    }

    @PostMapping
    public ResponseEntity<Object> createPerson(@RequestBody @Valid CreatePersonRequestDTO createPersonRequestDTO) {
        var user = userService.getById(createPersonRequestDTO.getUserId());
        var person = PersonDTOMapper.fromDTO(createPersonRequestDTO);
        person.setUser(user);
        person = personService.create(person);
        return new ResponseEntity<>(Map.of("id", person.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Object> deletePersonById(@PathVariable Long id) {
        var person = personService.getById(id);
        personService.delete(person);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(BY_ID)
    public ResponseEntity<PersonDTO> updatePersonById(
            @PathVariable Long id,
            @RequestBody @Valid CreatePersonRequestDTO createPersonRequestDTO) { // TODO
        var person = personService.getById(id);
        var user = userService.getById(createPersonRequestDTO.getUserId());
        var personDTO = PersonDTOMapper.makeDTO(createPersonRequestDTO);
        if (!Objects.equals(person.getUser().getId(), user.getId())) {
            person.setUser(user);
        }
        person.setPersonType(personDTO.getPersonType());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setMiddleName(personDTO.getMiddleName());
        person = personService.update(person);
        return ResponseEntity.ok(PersonDTOMapper.makeDTO(person));

    }
}
