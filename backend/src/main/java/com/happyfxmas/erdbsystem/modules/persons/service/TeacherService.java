package com.happyfxmas.erdbsystem.modules.persons.service;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.TeacherDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Position;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    TeacherDTO getTeacherDTOByPerson(Person person);

    List<Teacher> getAll();

    List<Teacher> getAllByPositionId(Long positionId);

    Teacher getById(Long id);

    Teacher getByIdWithResults(Long id);

    Teacher getByIdWithTasks(Long id);

    Teacher getByIdWithPersonAndPosition(Long id);

    Teacher getByPersonIdWithPosition(Long personId);

    Teacher getByPerson(Person person);

    Teacher create(Teacher teacher);

    void delete(Teacher teacher);

    Teacher update(Teacher teacherNew);

}
