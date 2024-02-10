package com.happyfxmas.erdbsystem.modules.ermodels.store.repos;

import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepo extends JpaRepository<Attribute, Long> {
}
