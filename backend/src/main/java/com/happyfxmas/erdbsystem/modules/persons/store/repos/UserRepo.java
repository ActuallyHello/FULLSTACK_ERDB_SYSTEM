package com.happyfxmas.erdbsystem.modules.persons.store.repos;

import com.happyfxmas.erdbsystem.modules.persons.store.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
}
