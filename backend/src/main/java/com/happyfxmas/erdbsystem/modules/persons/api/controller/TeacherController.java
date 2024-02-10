package com.happyfxmas.erdbsystem.modules.persons.api.controller;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.TeacherDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.request.CreateTeacherRequestDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.response.TeacherWithPersonDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.response.TeacherWithPositionDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PositionDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.TeacherDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.TeacherWithPersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.TeacherWithPositionDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.PersonNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.PositionNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.PositionServerException;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.TeacherNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PositionCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PositionDeleteException;
import com.happyfxmas.erdbsystem.modules.persons.service.PersonService;
import com.happyfxmas.erdbsystem.modules.persons.service.PositionService;
import com.happyfxmas.erdbsystem.modules.persons.service.TeacherService;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Teacher;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final PersonService personService;
    private final PositionService positionService;

    private static final String BY_ID = "/{id}";
    private static final String BY_PERSON_ID = "/persons/{personId}";
    private static final String BY_POSITION_ID = "/positions/{positionId}";

    @GetMapping(BY_ID)
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id) {
        var teacher = teacherService.getByIdWithPersonAndPosition(id);
        var personDTO = PersonDTOMapper.makeDTO(teacher.getPerson());
        var positionDTO = PositionDTOMapper.makeDTO(teacher.getPosition());
        var teacherDTO = TeacherDTOMapper.makeDTO(teacher, personDTO, positionDTO);
        return ResponseEntity.ok(teacherDTO);
    }

    @GetMapping(BY_PERSON_ID)
    public ResponseEntity<TeacherWithPositionDTO> getTeacherByPersonId(@PathVariable Long personId) {
        var teacher = teacherService.getByPersonIdWithPosition(personId);
        var positionDTO = PositionDTOMapper.makeDTO(teacher.getPosition());
        var teacherDTO = TeacherWithPositionDTOMapper.makeDTO(teacher, positionDTO);
        return ResponseEntity.ok(teacherDTO);
    }

    @GetMapping(BY_POSITION_ID)
    public ResponseEntity<List<TeacherWithPersonDTO>> getTeachersByPositionId(@PathVariable Long positionId) {
        var teacherList = teacherService.getAllByPositionId(positionId);
        var teacherDTOList = TeacherWithPersonDTOMapper.makeDTOs(teacherList);
        return ResponseEntity.ok(teacherDTOList);
    }

    @PostMapping
    public ResponseEntity<Object> createTeacher(@RequestBody @Valid CreateTeacherRequestDTO createTeacherRequestDTO) {
        var person = personService.getById(createTeacherRequestDTO.getPersonId());
        var position = positionService.getByIdWithTeachers(createTeacherRequestDTO.getPositionId());
        var teacher = Teacher.builder().person(person).build();
        position.addTeacher(teacher);
        teacher = teacherService.create(teacher);
        return new ResponseEntity<>(Map.of("id", teacher.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Object> deleteTeacherById(@PathVariable Long id) {
        var teacher = teacherService.getById(id);
        teacherService.delete(teacher);
        return ResponseEntity.noContent().build();
    }
}
