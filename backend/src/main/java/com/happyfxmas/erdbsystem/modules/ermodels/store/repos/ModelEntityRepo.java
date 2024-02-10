package com.happyfxmas.erdbsystem.modules.ermodels.store.repos;

import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelEntityRepo extends JpaRepository<ModelEntity, Long> {

    @Query("""
           select e from ModelEntity e
           inner join fetch e.attributeList
           where e.model = :model
           """
    )
    List<ModelEntity> getAllByModel(Model model);
}
