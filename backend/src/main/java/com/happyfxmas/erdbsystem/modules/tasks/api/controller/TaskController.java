package com.happyfxmas.erdbsystem.modules.tasks.api.controller;

import com.happyfxmas.erdbsystem.externals.exception.LoadTestDataException;
import com.happyfxmas.erdbsystem.modules.ermodels.exception.response.ModelNotFoundException;
import com.happyfxmas.erdbsystem.modules.ermodels.service.ModelService;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PositionDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.TeacherDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.StudentNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.TeacherNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.service.StudentService;
import com.happyfxmas.erdbsystem.modules.persons.service.TeacherService;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TestDataDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.request.CreateTaskRequestDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.request.SendTasksToStudentsDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.TaskWithResultDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.TaskWithTeacherDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.TaskWithTestDataDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.mapper.TaskDTOMapper;
import com.happyfxmas.erdbsystem.modules.tasks.api.mapper.TaskWithTeacherDTOMapper;
import com.happyfxmas.erdbsystem.modules.tasks.api.mapper.TaskWithTestDataDTOMapper;
import com.happyfxmas.erdbsystem.modules.tasks.exception.response.TaskNotFoundException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.response.TaskServerException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.ConvertEntityToJsonException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.DenormalizeModelCreationException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.TaskCreationException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.TaskDeleteException;
import com.happyfxmas.erdbsystem.modules.tasks.service.TaskDataGeneratorService;
import com.happyfxmas.erdbsystem.modules.tasks.service.TaskService;
import com.happyfxmas.erdbsystem.modules.tasks.service.TaskStudentService;
import com.happyfxmas.erdbsystem.modules.tasks.service.facade.TaskFacade;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskDataGeneratorService taskDataGeneratorService;
    private final TaskStudentService taskStudentService;
    private final TeacherService teacherService;
    private final ModelService modelService;
    private final StudentService studentService;
    private final TaskFacade taskFacade;

    private static final String BY_ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<TaskWithTeacherDTO>> getAllTasksWithTeachers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        var taskWithTeacherList = taskFacade.getTasksWithTeachersPageable(page, size);
        var taskWithTeacherDTOList = TaskWithTeacherDTOMapper.makeDTOs(taskWithTeacherList);
        return ResponseEntity.ok(taskWithTeacherDTOList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<TaskWithTestDataDTO> getTaskWithDenormalizeModelById(@PathVariable Long id) {
        var task = taskService.getByIdWithDenormalizeModel(id);
        TestDataDTO testDataDTO = taskDataGeneratorService.generateDataForTask(task);
        var taskWithTestDataDTO = TaskWithTestDataDTOMapper.makeDTO(task, testDataDTO);
        return ResponseEntity.ok(taskWithTestDataDTO);
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<TaskWithResultDTO>> getAllTasksForStudent(@PathVariable Long studentId) {
        var student = studentService.getByIdWithPersonAndGroup(studentId);
        var taskWithResultDTOList = taskStudentService.getTasksWithResultsDTOByStudent(student);
        return ResponseEntity.ok(taskWithResultDTOList);
    }

    @PostMapping("/send")
    public ResponseEntity<Object> sendTasksToStudents(@RequestBody @Valid SendTasksToStudentsDTO sendTasksToStudentsDTO) {
        var taskList = taskService.getByIdsWithTaskStudentList(sendTasksToStudentsDTO.getStudentIds());
        var studentList = sendTasksToStudentsDTO.getStudentIds().stream() // FIND LOG INFO {} TO CHANGE
                .map(studentService::getByIdWithTaskStudentList)
                .toList();
        taskStudentService.sendTasksToStudents(taskList, studentList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody @Valid CreateTaskRequestDTO createTaskRequestDTO) {
        var task = TaskDTOMapper.fromDTO(createTaskRequestDTO);
        var teacher = teacherService.getByIdWithTasks(createTaskRequestDTO.getTeacherId());
        var modelList = modelService.getByIds(createTaskRequestDTO.getModelIds());
        teacher.addTask(task);
        task = taskFacade.createTaskForModels(task, modelList);
        return ResponseEntity.ok(Map.of("taskId", task.getId()));
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Object> deleteTaskById(@PathVariable Long id) {
        var task = taskService.getByIdWithDenormalizeModel(id);
        taskService.deleteTask(task);
        return ResponseEntity.noContent().build();
    }
}
