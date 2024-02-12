package com.happyfxmas.erdbsystem.modules.ermodels.service;

import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ModelService {

    List<Model> getAll(Pageable pageable, Boolean includeStudents, Boolean includeTaskResults);

    Model getById(Long id);

    Model getByIdWithPerson(Long id);

    List<Model> getByIds(List<Long> id);

    List<Model> getAllByPerson(Person person);

    void deleteModel(Model model);

    Model create(Model model);
}
