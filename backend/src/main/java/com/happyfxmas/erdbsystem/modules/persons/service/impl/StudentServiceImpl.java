package com.happyfxmas.erdbsystem.modules.persons.service.impl;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.StudentDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.GroupDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.StudentDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.StudentNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.StudentCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.StudentDeleteException;
import com.happyfxmas.erdbsystem.modules.persons.service.StudentService;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Group;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Student;
import com.happyfxmas.erdbsystem.modules.persons.store.repos.StudentRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private StudentRepo studentRepo;

    @Override
    public List<Student> getAllByGroupIdWithPerson(Long groupId) {
        var studentList = studentRepo.findAllByGroupIdWithPerson(groupId);
        log.debug("GET STUDENTS BY GROUP WITH ID={} ({})", groupId, studentList.size());
        return studentList;
    }

    @Override
    public Student getById(Long id) {
        var student = studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id=" + id + " was not found!"));
        log.debug("GET STUDENT WITH ID={}", id);
        return student;
    }

    @Override
    public Student getByIdWithPersonAndGroup(Long id) {
        var student = studentRepo.findByIdWithPersonAndGroup(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id=" + id + " was not found!"));
        log.debug("GET STUDENT WITH ID={}", id);
        return student;
    }

    @Override
    public Student getByPersonIdWithGroup(Long personId) {
        var student = studentRepo.findByPersonIdWithGroup(personId)
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student with person id=" + personId + " was not found!"));
        log.debug("GET STUDENT BY PERSON WITH ID={}", personId);
        return student;
    }

    @Override
    public StudentDTO getStudentDTOByPerson(Person person) {
        var student = studentRepo.findByPersonIdWithGroup(person.getId());
        log.debug("GET STUDENT BY PERSON WITH ID={}", person.getId());
        return student.map(s -> StudentDTOMapper.makeDTO(
                s,
                GroupDTOMapper.makeDTO(s.getGroup()),
                PersonDTOMapper.makeDTO(person))
        ).orElse(null);
    }

    @Override
    public Student getByIdWithTaskStudentList(Long id) {
        var student = studentRepo.findByIdWithTaskList(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id=" + id + " was not found!"));
        log.debug("GET STUDENT WITH ID={}", id);
        return student;
    }

    @Override
    @Transactional
    public Student create(Student student) {
        try {
            student = studentRepo.saveAndFlush(student);
            log.info("STUDENT WITH ID={} WAS CREATED", student.getId());
            return student;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING STUDENT: {}", exception.getMessage());
            throw new StudentCreationException("Error when creating student! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(Student student) {
        try {
            studentRepo.delete(student);
            studentRepo.flush();
            log.info("STUDENT WITH ID={} WAS DELETED", student.getId());
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN DELETING STUDENT: {}", exception.getMessage());
            throw new StudentDeleteException("Error when deleting student! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Student update(Student studentNew) {
        try {
            var student = studentRepo.saveAndFlush(studentNew);
            log.info("STUDENT WITH ID={} WAS UPDATED", studentNew.getId());
            return student;
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN UPDATING STUDENT: {}", exception.getMessage());
            throw new StudentCreationException("Error when updating student! [DatabaseException]", exception);
        }
    }
}
