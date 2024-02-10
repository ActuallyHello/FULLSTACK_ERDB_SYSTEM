package com.happyfxmas.erdbsystem.modules.tasks.service.facade;

import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.response.ModelDetailDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.service.facade.ModelEntityRelationFacade;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.StudentDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.TeacherDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PositionDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.TeacherDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.service.StudentService;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Teacher;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TaskDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.ResultWithModelDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.ResultWithTaskDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.mapper.ResultWithModelDTOMapper;
import com.happyfxmas.erdbsystem.modules.tasks.api.mapper.ResultWithTaskDTOMapper;
import com.happyfxmas.erdbsystem.modules.tasks.api.mapper.TaskDTOMapper;
import com.happyfxmas.erdbsystem.modules.tasks.service.ResultService;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class ResultFacade {
    private final ResultService resultService;
    private final ModelEntityRelationFacade modelEntityRelationFacade;
    private final StudentService studentService;

    public List<Result> getAllPreview(Integer page, Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 20 : size;
        var pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        return resultService.getAllWithTaskAndModelAndTeacher(pageable);
    }

    public List<ResultWithTaskDTO> aggregateResultWithTasks(List<Result> resultList) {
        return resultList.stream()
                .map(result -> {
                    TaskDTO taskDTO = TaskDTOMapper.makeDTO(result.getTask());
                    Person authorResult = result.getModel().getPerson();
                    StudentDTO studentDTO = studentService.getStudentDTOByPerson(authorResult);
                    TeacherDTO teacherDTO = result.getTeacher() != null
                            ? TeacherDTOMapper.makeDTO(
                            result.getTeacher(),
                            PersonDTOMapper.makeDTO(result.getTeacher().getPerson()),
                            PositionDTOMapper.makeDTO(result.getTeacher().getPosition()))
                            : null;
                    return ResultWithTaskDTOMapper.makeDTO(result, taskDTO, studentDTO, teacherDTO);
                }).toList();
    }

    public ResultWithModelDTO getByIdToEvaluateResult(Result result) {
        Model sourceModelFromTask = result.getTask()
                .getDenormalizeModelList().get(0)
                .getModel();
        ModelDetailDTO sourceModelDetailDTO = modelEntityRelationFacade.getModelDetailDTOByModel(sourceModelFromTask);
        Model resultModelFromStudent = result.getModel();
        ModelDetailDTO resultModelDetailDTO = modelEntityRelationFacade.getModelDetailDTOByModel(resultModelFromStudent);
        TaskDTO taskDTO = TaskDTOMapper.makeDTO(result.getTask());

        Teacher teacher = result.getTeacher();
        TeacherDTO teacherDTO = teacher != null
                ? TeacherDTOMapper.makeDTO(
                        teacher,
                        PersonDTOMapper.makeDTO(teacher.getPerson()),
                        PositionDTOMapper.makeDTO(teacher.getPosition()))
                : null;
        return ResultWithModelDTOMapper.makeDTO(
                result,
                sourceModelDetailDTO,
                resultModelDetailDTO,
                taskDTO,
                teacherDTO);
    }

}
