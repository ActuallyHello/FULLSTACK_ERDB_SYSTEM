package com.happyfxmas.erdbsystem.modules.tasks.service.facade;

import com.happyfxmas.erdbsystem.modules.ermodels.exception.response.ModelNotFoundException;
import com.happyfxmas.erdbsystem.modules.ermodels.service.ModelEntityAttributeService;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.TeacherNotFoundException;
import com.happyfxmas.erdbsystem.modules.tasks.api.mapper.TaskDTOMapper;
import com.happyfxmas.erdbsystem.modules.tasks.exception.response.TaskServerException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.DenormalizeModelCreationException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.TaskCreationException;
import com.happyfxmas.erdbsystem.modules.tasks.service.DenormalizeModelService;
import com.happyfxmas.erdbsystem.modules.tasks.service.TaskService;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class TaskFacade {

    private final TaskService taskService;
    private final DenormalizeModelService denormalizeModelService;
    private final ModelEntityAttributeService modelEntityAttributeService;

    public List<Task> getTasksWithTeachersPageable(Integer page, Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 20 : size;
        var pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return taskService.getAllWithTeachers(pageable);
    }

    public Task createTaskForModels(Task task, List<Model> modelList) {
        for (var model : modelList) {
            var denormalizeModel = denormalizeModelService.getByModelWithTasks(model)
                    .orElseGet(() -> {
                        model.setModelEntityList(modelEntityAttributeService.getAllByModel(model)); // load entities
                        return denormalizeModelService.create(model);
                    });
            task.addDenormalizeModel(denormalizeModel);
        }
        task = taskService.create(task);
        return task;
    }
}
