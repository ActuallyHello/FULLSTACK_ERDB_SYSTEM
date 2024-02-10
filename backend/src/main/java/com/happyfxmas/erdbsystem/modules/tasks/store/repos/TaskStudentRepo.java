package com.happyfxmas.erdbsystem.modules.tasks.store.repos;

import com.happyfxmas.erdbsystem.modules.persons.store.models.Student;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.TaskStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskStudentRepo extends JpaRepository<TaskStudent, Long> {

    @Query("""
            select task from Task task
                inner join task.taskStudentList taskStudent
                inner join fetch task.teacher teacher
                inner join fetch teacher.person
                inner join fetch teacher.position
                left join fetch task.resultList
            where taskStudent.student = :student
            """)
    List<Task> findAllTasksWithTeachersAndResultsByStudent(Student student);
}
