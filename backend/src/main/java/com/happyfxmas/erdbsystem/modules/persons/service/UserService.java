package com.happyfxmas.erdbsystem.modules.persons.service;

import com.happyfxmas.erdbsystem.modules.persons.store.models.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(Long id);

    User getByLogin(String login);

    User create(User user);

    void delete(User user);

    User update(User userNew);
}
