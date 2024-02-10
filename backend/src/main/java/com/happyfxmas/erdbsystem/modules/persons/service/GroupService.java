package com.happyfxmas.erdbsystem.modules.persons.service;

import com.happyfxmas.erdbsystem.modules.persons.store.models.Group;

import java.util.List;

public interface GroupService {
    List<Group> getAll();

    List<Group> getAll(Boolean isActive);

    Group getById(Long id);

    Group getByIdWithStudents(Long id);

    Group create(Group group);

    void delete(Group group);

    Group update(Group groupNew);
}
