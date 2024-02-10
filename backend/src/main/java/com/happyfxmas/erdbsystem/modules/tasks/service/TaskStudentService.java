package com.happyfxmas.erdbsystem.modules.tasks.service;


import com.happyfxmas.erdbsystem.modules.persons.store.models.Student;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.TaskWithResultDTO;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;

import java.util.List;

public interface TaskStudentService {

    List<Task> getTasksWithTeachersAndResultsByStudent(Student student);

    List<TaskWithResultDTO> getTasksWithResultsDTOByStudent(Student student);

    void sendTasksToStudents(List<Task> taskList, List<Student> studentList);
}
