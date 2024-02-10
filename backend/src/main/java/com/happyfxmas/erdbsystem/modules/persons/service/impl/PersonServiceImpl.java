package com.happyfxmas.erdbsystem.modules.persons.service.impl;

import com.happyfxmas.erdbsystem.modules.persons.exception.service.PersonCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PersonDeleteException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PersonDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.service.PersonService;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.persons.store.models.User;
import com.happyfxmas.erdbsystem.modules.persons.store.repos.PersonRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private PersonRepo personRepo;

    @Override
    public List<Person> getAll() {
        var personList = personRepo.findAll();
        log.debug("GET PERSONS ({})", personList.size());
        return personList;
    }

    @Override
    public List<Person> getAllNotUsed() {
        var personList = personRepo.findAllNotUsed();
        log.debug("GET PERSONS ({})", personList.size());
        return personList;
    }

    @Override
    public Person getById(Long id) {
        var person = personRepo.findById(id)
                .orElseThrow(() -> new PersonDoesNotExistException("Person with id=" + id + " was not found"));
        log.debug("GET PERSON BY ID={}", id);
        return person;
    }

    @Override
    public Person getByIdWithModels(Long id) {
        var person = personRepo.findByIdWithModels(id)
                .orElseThrow(() -> new PersonDoesNotExistException("Person with id=" + id + " was not found"));
        log.debug("GET PERSON BY ID={} WITH MODELS", id);
        return person;
    }

    @Override
    public Optional<Person> getByUser(User user) {
        var person = personRepo.findByUser(user);
        log.debug("GET PERSON BY USER WITH ID={}", user.getId());
        return person;
    }

    @Override
    @Transactional
    public Person create(Person person) {
        try {
            person = personRepo.saveAndFlush(person);
            log.info("CREATED PERSON WITH ID={}", person.getId());
            return person;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING PERSON: {}", exception.getMessage());
            throw new PersonCreationException("Error when creating person! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(Person person) {
        try {
            personRepo.delete(person);
            personRepo.flush();
            log.info("PERSON WITH ID={} WAS DELETED", person.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING PERSON! {}", exception.getMessage());
            throw new PersonDeleteException("Error when deleting person! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Person update(Person person) {
        try {
            person = personRepo.saveAndFlush(person);
            log.info("PERSON WITH ID={} WAS UPDATED", person.getId());
            return person;
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN UPDATING PERSON! {}", exception.getMessage());
            throw new PersonCreationException("Error when updating person! [DatabaseException]", exception);
        }
    }


}
