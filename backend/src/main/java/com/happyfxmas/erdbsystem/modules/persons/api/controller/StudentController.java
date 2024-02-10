package com.happyfxmas.erdbsystem.modules.persons.api.controller;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.StudentDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.request.CreateStudentRequestDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.response.StudentWithGroupDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.response.StudentWithPersonDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.GroupDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.StudentDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.StudentWithGroupDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.StudentWithPersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.GroupNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.PersonNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.StudentNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.StudentServerException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.StudentCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.StudentDeleteException;
import com.happyfxmas.erdbsystem.modules.persons.service.GroupService;
import com.happyfxmas.erdbsystem.modules.persons.service.PersonService;
import com.happyfxmas.erdbsystem.modules.persons.service.StudentService;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Student;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final GroupService groupService;
    private final PersonService personService;

    private static final String BY_ID = "/{id}";
    private static final String BY_PERSON_ID = "/persons/{personId}";
    private static final String BY_GROUP_ID = "/groups/{groupId}";

    @GetMapping(BY_ID)
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        var student = studentService.getByIdWithPersonAndGroup(id);
        var groupDTO = GroupDTOMapper.makeDTO(student.getGroup());
        var personDTO = PersonDTOMapper.makeDTO(student.getPerson());
        var studentDTO = StudentDTOMapper.makeDTO(student, groupDTO, personDTO);
        return ResponseEntity.ok(studentDTO);
    }

    @GetMapping(BY_PERSON_ID)
    public ResponseEntity<StudentWithGroupDTO> getStudentByPersonId(@PathVariable Long personId) {
        var student = studentService.getByPersonIdWithGroup(personId);
        var groupDTO = GroupDTOMapper.makeDTO(student.getGroup());
        var studentWithGroupDTO = StudentWithGroupDTOMapper.makeDTO(student, groupDTO);
        return ResponseEntity.ok(studentWithGroupDTO);
    }

    @GetMapping(BY_GROUP_ID)
    public ResponseEntity<List<StudentWithPersonDTO>> getStudentsByGroupId(@PathVariable Long groupId) {
        var studentList = studentService.getAllByGroupIdWithPerson(groupId);
        var studentDTOList = StudentWithPersonDTOMapper.makeDTOs(studentList);
        return ResponseEntity.ok(studentDTOList);
    }

    @PostMapping
    public ResponseEntity<Object> createStudent(@RequestBody @Valid CreateStudentRequestDTO createStudentRequestDTO) {
        var group = groupService.getByIdWithStudents(createStudentRequestDTO.getGroupId());
        var person = personService.getById(createStudentRequestDTO.getPersonId());
        var student = Student.builder().person(person).build();
        group.addStudent(student);
        student = studentService.create(student);
        return ResponseEntity.ok(Map.of("id", student.getId()));
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Object> deleteStudentById(@PathVariable Long id) {
        var student = studentService.getById(id);
        studentService.delete(student);
        return ResponseEntity.noContent().build();
    }
}
