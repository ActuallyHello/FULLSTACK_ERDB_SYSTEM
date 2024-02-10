package com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.TeacherDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TaskWithTeacherDTO extends TaskDTO {
    private TeacherDTO teacherDTO;
}
