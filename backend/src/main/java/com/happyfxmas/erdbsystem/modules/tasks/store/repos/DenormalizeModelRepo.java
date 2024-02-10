package com.happyfxmas.erdbsystem.modules.tasks.store.repos;

import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.DenormalizeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DenormalizeModelRepo extends JpaRepository<DenormalizeModel, Long> {

    @Query("""
            select denormalizeModel from DenormalizeModel denormalizeModel
                inner join fetch denormalizeModel.taskList
            where denormalizeModel.model = :model
            """)
    Optional<DenormalizeModel> findByModelIdWithTasks(Model model);
}
