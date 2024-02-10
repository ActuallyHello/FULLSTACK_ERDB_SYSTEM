package com.happyfxmas.erdbsystem.modules.persons.service;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.StudentDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Group;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllByGroupIdWithPerson(Long groupId);

    Student getById(Long id);

    Student getByPersonIdWithGroup(Long personId);

    StudentDTO getStudentDTOByPerson(Person person);

    Student getByIdWithPersonAndGroup(Long id);

    Student create(Student student);

    void delete(Student student);

    Student update(Student studentNew);

    Student getByIdWithTaskStudentList(Long id);
}
