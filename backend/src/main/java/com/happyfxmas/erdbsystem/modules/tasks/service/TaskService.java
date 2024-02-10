package com.happyfxmas.erdbsystem.modules.tasks.service;

import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TestDataDTO;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAllWithTeachers(Pageable pageable);

    Task getByIdWithDenormalizeModel(Long id);

    Task getByIdWithTaskStudentList(Long id);

    List<Task> getByIdsWithTaskStudentList(List<Long> idList);

    Task getByIdWithResults(Long id);

    Task create(Task task);

    void deleteTask(Task task);

}
