package com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response;

import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TaskDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TestDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
public class TaskWithTestDataDTO extends TaskDTO {
    public final TestDataDTO testDataDTO;
}
