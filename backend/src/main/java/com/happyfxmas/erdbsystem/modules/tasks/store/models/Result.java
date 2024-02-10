package com.happyfxmas.erdbsystem.modules.tasks.store.models;

import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Teacher;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.enums.Mark;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"model", "teacher"})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Mark mark;
    @Column(updatable = false)
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    private Model model;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    private Task task;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Teacher teacher;

    public void setModel(Model model) {
        this.model = model;
        model.getResultList().add(this);
    }

    public void setTask(Task task) {
        this.task = task;
        task.getResultList().add(this);
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getResultList().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return id != null && id.equals(result.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
