package com.happyfxmas.erdbsystem.modules.ermodels.store.models;

import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Result;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
@ToString(exclude = {"modelEntityList", "person", "resultList"})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="model")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private String topic;
    @Column(updatable = false)
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
    @Column(nullable = false)
    @Builder.Default
    private Boolean isTaskResult = false;

    @OneToMany(
            mappedBy = "model",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<ModelEntity> modelEntityList = new ArrayList<>();

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    private Person person;

    @OneToMany(
            mappedBy = "model",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Result> resultList = new ArrayList<>();

    public void addModelEntity(@NonNull ModelEntity modelEntity) {
        this.modelEntityList.add(modelEntity);
        modelEntity.setModel(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return id != null && id.equals(model.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
