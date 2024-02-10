package com.happyfxmas.erdbsystem.modules.ermodels.service.impl;

import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.RelationDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.exception.service.RelationCreationException;
import com.happyfxmas.erdbsystem.modules.ermodels.exception.service.RelationDeleteException;
import com.happyfxmas.erdbsystem.modules.ermodels.exception.validation.RelationDoesNotMatchEntityException;
import com.happyfxmas.erdbsystem.modules.ermodels.service.RelationService;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.ModelEntity;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Relation;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.enums.Power;
import com.happyfxmas.erdbsystem.modules.ermodels.store.repos.RelationRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class RelationServiceImpl implements RelationService {

    private RelationRepo relationRepo;

    @Override
    @Transactional
    public List<Relation> createEntitiesRelations(List<Relation> relationList) {
        try {
            relationRepo.saveAllAndFlush(relationList);
            log.info("CREATED ENTITY RELATIONS ({})", relationList.size());
            return relationList;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING RELATIONS! {}", exception.getMessage());
            throw new RelationCreationException("Error when creating relations! [DatabaseException]", exception);
        }
    }

    public List<Relation> collectRelationsBetweenEntities(List<RelationDTO> relationDTOList,
                                                           List<ModelEntity> modelEntityList) {
        List<Relation> relationList = new ArrayList<>();
        for (var relationDTO : relationDTOList) {
            ModelEntity fromEntity = null;
            ModelEntity toEntity = null;
            for (var modelEntity : modelEntityList) {
                if (fromEntity != null && toEntity != null) { break; }
                if (relationDTO.getFromEntity().equals(modelEntity.getTitle())) {
                    fromEntity = modelEntity;
                }
                if (relationDTO.getToEntity().equals(modelEntity.getTitle())) {
                    toEntity = modelEntity;
                }
            }
            if (fromEntity == null || toEntity == null) {
                log.error("RELATION WITH table1={} AND table2={} DOES NOT MATCH WITH ENTITIES!",
                        relationDTO.getFromEntity(),
                        relationDTO.getToEntity());
                throw new RelationDoesNotMatchEntityException(("Relation with table1=%s and table2=%s " +
                        "does not match with entities in table! [ValidationException]").formatted(
                        relationDTO.getFromEntity(), relationDTO.getToEntity()
                ));
            }
            relationList.add(Relation.builder()
                    .modelEntity1(fromEntity)
                    .modelEntity2(toEntity)
                    .power(Power.fromString(relationDTO.getPower()))
                    .build());
        }
        return relationList;
    }

    @Override
    @Transactional
    public void deleteRelationsFromModel(Model model) {
        var modelEntityList = model.getModelEntityList();
        if (modelEntityList == null || modelEntityList.isEmpty()) {
            log.warn("THERE ARE NO ENTITIES IN MODEL WITH ID={}", model.getId());
            return;
        }
        var relationList = this.getRelationsByEntityIds(modelEntityList.stream()
                .map(ModelEntity::getId)
                .toList());
        try {
            relationRepo.deleteAll(relationList);
            relationRepo.flush();
            log.info("RELATIONS WERE DELETED FROM MODEL WITH ID={}", model.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING RELATIONS FROM MODEL WITH ID={}! {}", model.getId(), exception.getMessage());
            throw new RelationDeleteException(
                    "Error when deleting relations from model! [DatabaseException]", exception);
        }
    }

    @Override
    public List<Relation> getRelationsByEntityIds(List<Long> modelEntityIdList) {
        var relationList =
                relationRepo.findByModelEntity1IdOrModelEntity2IdInModelEntityIdList(modelEntityIdList);
        log.debug("GET RELATIONS ({})", relationList.size());
        return relationList;
    }
}
