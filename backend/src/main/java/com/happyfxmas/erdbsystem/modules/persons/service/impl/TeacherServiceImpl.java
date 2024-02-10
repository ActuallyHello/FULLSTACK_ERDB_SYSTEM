package com.happyfxmas.erdbsystem.modules.persons.service.impl;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.TeacherDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PositionDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.TeacherDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.TeacherNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.TeacherCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.TeacherDeleteException;
import com.happyfxmas.erdbsystem.modules.persons.service.TeacherService;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Position;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Teacher;
import com.happyfxmas.erdbsystem.modules.persons.store.repos.TeacherRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepo teacherRepo;

    @Override
    public TeacherDTO getTeacherDTOByPerson(Person person) {
        var teacher = teacherRepo.findByPersonIdWithPosition(person.getId());
        log.debug("GET TEACHER BY PERSON WITH ID={}", person.getId());
        return teacher.map(t -> TeacherDTOMapper.makeDTO(
                t,
                PersonDTOMapper.makeDTO(person),
                PositionDTOMapper.makeDTO(t.getPosition()))
        ).orElse(null);
    }

    @Override
    public List<Teacher> getAll() {
        var teacherList = teacherRepo.findAll();
        log.debug("GET TEACHER ({})", teacherList.size());
        return teacherList;
    }

    @Override
    public List<Teacher> getAllByPositionId(Long positionId) {
        var teacherList = teacherRepo.findAllByPositionIdWithPerson(positionId);
        log.debug("GET TEACHER BY POSITION WITH ID={} ({})", positionId, teacherList.size());
        return teacherList;
    }

    @Override
    public Teacher getById(Long id) {
        var teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id=" + id + " was not found!"));
        log.debug("GET TEACHER WITH ID={}", id);
        return teacher;
    }

    @Override
    public Teacher getByIdWithResults(Long id) {
        var teacher = teacherRepo.findByIdWithResults(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id=" + id + " was not found!"));
        log.debug("GET TEACHER WITH ID={}", id);
        return teacher;
    }

    @Override
    public Teacher getByIdWithTasks(Long id) {
        var teacher = teacherRepo.findByIdWithTasks(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id=" + id + " was not found!"));
        log.debug("GET TEACHER WITH ID={}", id);
        return teacher;
    }

    @Override
    public Teacher getByIdWithPersonAndPosition(Long id) {
        var teacher = teacherRepo.findByIdWithPersonAndPosition(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id=" + id + " was not found!"));
        log.debug("GET TEACHER WITH ID={}", id);
        return teacher;
    }

    @Override
    public Teacher getByPersonIdWithPosition(Long personId) {
        var teacher = teacherRepo.findByPersonIdWithPosition(personId)
                .orElseThrow(() -> new TeacherNotFoundException(
                        "Teacher with person id=" + personId + " was not found!"));
        log.debug("GET TEACHER BY PERSON WITH ID={}", personId);
        return teacher;
    }

    @Override
    public Teacher getByPerson(Person person) {
        var teacher = teacherRepo.findByPerson(person)
                .orElseThrow(() -> new TeacherNotFoundException(
                        "Teacher with person id=" + person.getId() + " was not found!"));
        log.debug("GET TEACHER BY PERSON WITH ID={}", person.getId());
        return teacher;
    }

    @Override
    @Transactional
    public Teacher create(Teacher teacher) {
        try {
            teacher = teacherRepo.saveAndFlush(teacher);
            log.info("CREATE TEACHER WITH ID={}", teacher.getId());
            return teacher;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING TEACHER: {}", exception.getMessage());
            throw new TeacherCreationException("Error when creating teacher! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(Teacher teacher) {
        try {
            teacherRepo.delete(teacher);
            teacherRepo.flush();
            log.info("TEACHER WITH ID={} WAS DELETED", teacher.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING TEACHER: {}", exception.getMessage());
            throw new TeacherDeleteException("Error when deleting teacher! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Teacher update(Teacher teacherNew) {
        try {
            var teacher = teacherRepo.saveAndFlush(teacherNew);
            log.info("TEACHER WITH ID={} WAS UPDATED", teacher.getId());
            return teacher;
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN UPDATING TEACHER: {}", exception.getMessage());
            throw new TeacherDeleteException("Error when updating teacher! [DatabaseException]", exception);
        }
    }
}
