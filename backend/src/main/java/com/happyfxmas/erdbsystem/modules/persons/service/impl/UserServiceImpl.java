package com.happyfxmas.erdbsystem.modules.persons.service.impl;

import com.happyfxmas.erdbsystem.modules.persons.exception.response.UserNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.UserCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.UserDeleteException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.UserDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.service.UserService;
import com.happyfxmas.erdbsystem.modules.persons.store.models.User;
import com.happyfxmas.erdbsystem.modules.persons.store.repos.UserRepo;
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
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Override
    public List<User> getAll() {
        List<User> userList = userRepo.findAll();
        log.debug("GET USERS ({})", userList.size());
        return userList;
    }

    @Override
    public User getById(Long id) {
        var user = userRepo.findById(id)
                .orElseThrow(() -> new UserDoesNotExistException("User with id=" + id + " was not found!"));
        log.debug("GET USER WITH ID={}", id);
        return user;
    }

    @Override
    public User getByLogin(String login) {
        var user = userRepo.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User with login=" + login + "was not found!"));
        log.debug("GET USER BY LOGIN={}", log);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        try {
            user = userRepo.saveAndFlush(user);
            log.info("USER WITH ID={} WAS CREATED", user.getId());
            return user;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING USER: {}", exception.getMessage());
            throw new UserCreationException("Error when creating user! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(User user) {
        try {
            userRepo.delete(user);
            userRepo.flush();
            log.info("USER WITH ID={} WAS DELETED", user.getId());
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN DELETING USER: {}", exception.getMessage());
            throw new UserDeleteException("Error when deleting user! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public User update(User userNew) {
        try {
            User user = userRepo.saveAndFlush(userNew);
            log.info("USER WITH ID={} WAS UPDATED", userNew.getId());
            return user;
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN UPDATING USER: {}", exception.getMessage());
            throw new UserCreationException("Error when updating user! [DatabaseException]", exception);
        }
    }
}
