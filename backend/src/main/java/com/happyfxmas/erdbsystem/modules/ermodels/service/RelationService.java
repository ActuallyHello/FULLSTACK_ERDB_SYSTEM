package com.happyfxmas.erdbsystem.modules.ermodels.service;


import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.RelationDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.ModelEntity;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Relation;

import java.util.List;

public interface RelationService {
    List<Relation> createEntitiesRelations(List<Relation> relationList);

    List<Relation> collectRelationsBetweenEntities(List<RelationDTO> relationDTOList, List<ModelEntity> modelEntityList);

    void deleteRelationsFromModel(Model model);

    List<Relation> getRelationsByEntityIds(List<Long> modelEntityIdList);
}
