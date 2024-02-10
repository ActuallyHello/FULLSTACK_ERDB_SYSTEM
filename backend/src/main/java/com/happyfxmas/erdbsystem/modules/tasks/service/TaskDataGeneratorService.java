package com.happyfxmas.erdbsystem.modules.tasks.service;

import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TestDataDTO;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;

public interface TaskDataGeneratorService {
    TestDataDTO generateDataForTask(Task task);
}
