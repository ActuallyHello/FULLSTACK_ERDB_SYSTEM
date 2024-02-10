package com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response;

import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.response.ModelDetailDTO;
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
public class ResultWithModelDTO extends ResultDTO {
    private ModelDetailDTO modelDetailSourceDTO;
    private ModelDetailDTO modelDetailResultDTO;
    private TaskDTO taskDTO;
    private TeacherDTO teacherDTO;
}
