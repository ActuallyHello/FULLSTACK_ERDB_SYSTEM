package com.happyfxmas.erdbsystem.modules.persons.service.impl;

import com.happyfxmas.erdbsystem.modules.persons.exception.response.GroupNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.GroupCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.GroupDeleteException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.GroupDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.service.GroupService;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Group;
import com.happyfxmas.erdbsystem.modules.persons.store.repos.GroupRepo;
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
public class GroupServiceImpl implements GroupService {

    private GroupRepo groupRepo;

    @Override
    public List<Group> getAll() {
        var groupList = groupRepo.findAll();
        log.debug("GET GROUPS ({})", groupList.size());
        return groupList;
    }

    @Override
    public List<Group> getAll(Boolean isActive) {
        var groupList = groupRepo.findByIsActive(isActive);
        log.debug("GET GROUPS BY ACTIVE={} ({})", isActive, groupList.size());
        return groupList;
    }

    @Override
    public Group getById(Long id) {
        var group = groupRepo.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group with id=" + id + " was not found!"));
        log.debug("GET GROUP WITH ID={}", id);
        return group;
    }

    @Override
    public Group getByIdWithStudents(Long id) {
        var group = groupRepo.findByIdWithStudents(id)
                .orElseThrow(() -> new GroupDoesNotExistException("Group with id=" + id + " was not found!"));
        log.debug("GET GROUP WITH ID={}", id);
        return group;
    }

    @Override
    @Transactional
    public Group create(Group group) {
        try {
            group = groupRepo.saveAndFlush(group);
            log.info("GROUP WITH ID={} WAS CREATED", group.getId());
            return group;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING GROUP: {}", exception.getMessage());
            throw new GroupCreationException("Error when creating group! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(Group group) {
        try {
            groupRepo.delete(group);
            groupRepo.flush();
            log.info("GROUP WITH ID={} WAS DELETED", group.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING GROUP: {}", exception.getMessage());
            throw new GroupDeleteException("Error when deleting group! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Group update(Group groupNew) {
        try {
            var group = groupRepo.saveAndFlush(groupNew);
            log.info("GROUP WITH ID={} WAS UPDATED", group.getId());
            return group;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN UPDATING A GROUP: {}", exception.getMessage());
            throw new GroupCreationException("Error when updating group! [DatabaseException]", exception);
        }
    }
}
