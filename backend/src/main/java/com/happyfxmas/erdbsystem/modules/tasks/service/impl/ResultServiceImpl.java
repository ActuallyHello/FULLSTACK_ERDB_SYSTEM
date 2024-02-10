package com.happyfxmas.erdbsystem.modules.tasks.service.impl;


import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Teacher;
import com.happyfxmas.erdbsystem.modules.tasks.exception.response.ResultNotFoundException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.ResultCreationException;
import com.happyfxmas.erdbsystem.modules.tasks.service.ResultService;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Result;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.enums.Mark;
import com.happyfxmas.erdbsystem.modules.tasks.store.repos.ResultRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class ResultServiceImpl implements ResultService {

    private final ResultRepo resultRepo;

    @Override
    public List<Result> getAllWithTaskAndModelAndTeacher(Pageable pageable) {
        var resultList = resultRepo.findAllWithTaskAndModelAndTeacher(pageable);
        log.debug("GET RESULTS ({}) PAGE={} SIZE={}", resultList.size(),
                pageable.getPageNumber(), pageable.getPageSize());
        return resultList;
    }

    @Override
    public Result getById(Long id) {
        var result = resultRepo.findById(id)
                .orElseThrow(() -> new ResultNotFoundException("Result with id=" + id + " was not found"));
        log.debug("GET RESULT (ID={})", id);
        return result;
    }

    @Override
    public Result getByIdWithModelAndTaskAndTeacher(Long id) {
        var result = resultRepo.findByIdWithModelAndTaskAndTeacher(id)
                .orElseThrow(() -> new ResultNotFoundException("Result with id=" + id + " was not found"));
        log.debug("GET RESULT (ID={})", id);
        return result;
    }

    @Override
    public Optional<Result> getLastByPersonAndTask(Person person, Task task) {
        var result = resultRepo.findLastByPersonAndTask(person, task);
        log.debug("GET RESULT BY PERSON (ID={})", person.getId());
        return result;
    }

    @Override
    @Transactional
    public void sendResult(Model model, Task task) {
        var result = new Result();
        result.setTask(task);
        result.setModel(model);
        try {
            result = resultRepo.saveAndFlush(result);
            log.info("RESULT WITH ID={} TO TASK WITH ID={} WAS SENT", result.getId(), task.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN SENDING RESULT FOR TASK WITH ID={}: {}",
                    task.getId(), exception.getMessage());
            throw new ResultCreationException("Error when sending result! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Result update(Result result, Mark mark, Teacher teacher) {
        result.setMark(mark);
        teacher.addResult(result);
        try {
            result = resultRepo.saveAndFlush(result);
            log.info("RESULT WITH ID={} WAS UPDATED!", result.getId());
            return result;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN UPDATING TASK: {}", exception.getMessage());
            throw new ResultCreationException("Error when updating result! [DatabaseException]", exception);
        }
    }
}
