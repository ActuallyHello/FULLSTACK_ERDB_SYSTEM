package com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.StudentDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.TeacherDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.ResultDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class ResultWithTaskDTO extends ResultDTO {
    private TaskDTO taskDTO;
    private StudentDTO studentDTO;
    private TeacherDTO teacherDTO;
}
