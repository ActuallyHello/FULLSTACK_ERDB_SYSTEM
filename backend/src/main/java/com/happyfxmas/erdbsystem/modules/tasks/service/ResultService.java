package com.happyfxmas.erdbsystem.modules.tasks.service;

import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Teacher;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Result;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.enums.Mark;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ResultService {

    List<Result> getAllWithTaskAndModelAndTeacher(Pageable pageable);

    Result getById(Long id);

    Result getByIdWithModelAndTaskAndTeacher(Long id);

    Optional<Result> getLastByPersonAndTask(Person person, Task task);

    void sendResult(Model model, Task task);

    Result update(Result result, Mark mark, Teacher teacher);
}
