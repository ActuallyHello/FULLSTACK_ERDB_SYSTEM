package com.happyfxmas.erdbsystem.modules.ermodels.api.mapper;

import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.RelationDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.request.CreateModelRequestDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.request.RelationRequestDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Relation;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.enums.Power;
import lombok.NonNull;

import java.util.List;

public class RelationDTOMapper {

    public static RelationDTO makeDTO(@NonNull Relation relation) {
        return RelationDTO.builder()
                .id(relation.getId())
                .fromEntity(relation.getModelEntity1().getTitle())
                .power(relation.getPower().getValue())
                .toEntity(relation.getModelEntity2().getTitle())
                .build();
    }

    public static RelationDTO makeDTO(@NonNull RelationRequestDTO relationRequestDTO) {
        return RelationDTO.builder()
                .fromEntity(relationRequestDTO.getFromEntity())
                .power(Power.fromString(relationRequestDTO.getPower()).getValue())
                .toEntity(relationRequestDTO.getToEntity())
                .build();
    }

    public static List<RelationDTO> makeDTOs(@NonNull CreateModelRequestDTO createModelRequestDTO) {
        return createModelRequestDTO.getRelationList().stream()
                .map(RelationDTOMapper::makeDTO)
                .toList();
    }
}
