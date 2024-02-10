package com.happyfxmas.erdbsystem.modules.tasks.store.models;

import com.happyfxmas.erdbsystem.modules.persons.store.models.Teacher;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.enums.Complexity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"teacher", "taskStudentList", "denormalizeModelList"})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private Complexity complexity;
    @Column
    private Integer testDataAmount;
    @Column(updatable = false)
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    private Teacher teacher;

    @OneToMany(mappedBy = "task", orphanRemoval = true)
    @Builder.Default
    private List<TaskStudent> taskStudentList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "task_denormalize_model",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "denormalize_model_id")
    )
    @Builder.Default
    private List<DenormalizeModel> denormalizeModelList = new ArrayList<>();

    @OneToMany(mappedBy = "task")
    @Builder.Default
    private List<Result> resultList = new ArrayList<>();

    public void addDenormalizeModel(DenormalizeModel denormalizeModel) {
        this.denormalizeModelList.add(denormalizeModel);
        denormalizeModel.getTaskList().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id != null && id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
