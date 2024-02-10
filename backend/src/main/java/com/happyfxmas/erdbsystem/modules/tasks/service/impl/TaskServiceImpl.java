package com.happyfxmas.erdbsystem.modules.tasks.service.impl;

import com.happyfxmas.erdbsystem.modules.ermodels.service.ModelEntityAttributeService;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.TaskCreationException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.TaskDeleteException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.TaskDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.tasks.service.DenormalizeModelService;
import com.happyfxmas.erdbsystem.modules.tasks.service.TaskService;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;
import com.happyfxmas.erdbsystem.modules.tasks.store.repos.TaskRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

    @Override
    public Task getById(Long id) {
        var task = taskRepo.findById(id)
                .orElseThrow(() -> new TaskDoesNotExistException("Task with id=" + id + " was not found!"));
        log.debug("GET TASK WITH ID={}", id);
        return task;
    }

    @Override
    public List<Task> getAllWithTeachers(Pageable pageable) {
        var taskWithTeacherList = taskRepo.findAllWithTeachers(pageable);
        log.debug("GET TASKS ({})", taskWithTeacherList.size());
        return taskWithTeacherList;
    }

    @Override
    public Task getByIdWithDenormalizeModel(Long id) {
        var task = taskRepo.findByIdWithDenormalizeModel(id)
                .orElseThrow(() -> new TaskDoesNotExistException("Task with id=" + id + " was not found!"));
        log.debug("GET TASK WITH ID={}", id);
        return task;
    }

    @Override
    public Task getByIdWithTaskStudentList(Long id) {
        var task = taskRepo.findByIdWithTaskStudentList(id)
                .orElseThrow(() -> new TaskDoesNotExistException("Task with id=" + id + " was not found!"));
        log.debug("GET TASK WITH ID={}", id);
        return task;
    }

    @Override
    public List<Task> getByIdsWithTaskStudentList(List<Long> idList) {
        var taskWithStudentList = idList.stream()
                .map(this::getByIdWithTaskStudentList)
                .toList();
        log.debug("GET TASKS ({})", taskWithStudentList.size());
        return taskWithStudentList;
    }

    @Override
    public Task getByIdWithResults(Long id) {
        var task = taskRepo.findByIdWithResults(id)
                .orElseThrow(() -> new TaskDoesNotExistException("Task with id=" + id + " was not found!"));
        log.debug("GET TASK WITH ID={}", id);
        return task;
    }

    @Override
    @Transactional
    public Task create(Task task) {
        try {
            task = taskRepo.saveAndFlush(task);
            log.info("CREATED TASK WITH ID={}", task.getId());
            return task;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING TASK: {}", exception.getMessage());
            throw new TaskCreationException("Error when creating task! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public void deleteTask(Task task) {
        try {
            taskRepo.delete(task);
            taskRepo.flush();
            log.info("TASK WITH ID={} WAS DELETED!", task.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING TASK WITH ID={}: {}", task.getId(), exception.getMessage());
            throw new TaskDeleteException("Error when deleting task! [DatabaseException]", exception);
        }
    }
}
