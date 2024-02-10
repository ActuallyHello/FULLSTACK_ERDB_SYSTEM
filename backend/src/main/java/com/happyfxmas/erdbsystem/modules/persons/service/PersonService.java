package com.happyfxmas.erdbsystem.modules.persons.service;

import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.persons.store.models.User;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> getAllNotUsed();

    Person getById(Long id);

    Person getByIdWithModels(Long id);

    Optional<Person> getByUser(User user);

    Person create(Person person);

    void delete(Person person);

    Person update(Person person);

    List<Person> getAll();
}
