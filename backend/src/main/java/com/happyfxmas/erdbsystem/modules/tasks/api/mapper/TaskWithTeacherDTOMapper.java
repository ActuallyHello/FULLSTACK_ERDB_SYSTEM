package com.happyfxmas.erdbsystem.modules.tasks.api.mapper;


import com.happyfxmas.erdbsystem.modules.persons.api.dto.TeacherDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PositionDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.TeacherDTOMapper;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.TaskWithTeacherDTO;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;

import java.util.List;

public class TaskWithTeacherDTOMapper {
    public static TaskWithTeacherDTO makeDTO(Task task, TeacherDTO teacherDTO) {
        return TaskWithTeacherDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .testDataAmount(task.getTestDataAmount())
                .complexity(task.getComplexity())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .teacherDTO(teacherDTO)
                .build();
    }

    public static List<TaskWithTeacherDTO> makeDTOs(List<Task> taskList) {
        return taskList.stream()
                .map(task -> {
                    var teacher = task.getTeacher();
                    var person = teacher.getPerson();
                    var position = teacher.getPosition();
                    return TaskWithTeacherDTOMapper.makeDTO(
                            task,
                            TeacherDTOMapper.makeDTO(
                                    teacher,
                                    PersonDTOMapper.makeDTO(person),
                                    PositionDTOMapper.makeDTO(position)));
                })
                .toList();
    }
}
