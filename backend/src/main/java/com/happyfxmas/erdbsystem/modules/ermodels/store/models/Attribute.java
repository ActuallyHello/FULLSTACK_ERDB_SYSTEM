package com.happyfxmas.erdbsystem.modules.ermodels.store.models;

import com.happyfxmas.erdbsystem.modules.ermodels.store.models.enums.AttributeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "modelEntity")
@Entity
@Table(name="attribute")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private AttributeType attributeType;

    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false
    )
    private ModelEntity modelEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return id != null && id.equals(attribute.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
