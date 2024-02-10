package com.happyfxmas.erdbsystem.modules.ermodels.api.mapper;

import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.AttributeDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.ModelEntityDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.request.CreateModelRequestDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.request.TableRequestDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.ModelEntity;
import lombok.NonNull;

import java.util.List;

public class ModelEntityDTOMapper {

    public static ModelEntityDTO makeDTO(@NonNull ModelEntity modelEntity,
                                         @NonNull List<AttributeDTO> attributeDTOList) {
        return ModelEntityDTO.builder()
                .id(modelEntity.getId())
                .title(modelEntity.getTitle())
                .attributeDTOList(attributeDTOList)
                .build();
    }

    public static ModelEntityDTO makeDTO(@NonNull TableRequestDTO tableRequestDTO,
                                         @NonNull List<AttributeDTO> attributeDTOList) {
        return ModelEntityDTO.builder()
                .title(tableRequestDTO.getTitle())
                .attributeDTOList(attributeDTOList)
                .build();
    }

    public static List<ModelEntityDTO> makeDTOs(@NonNull CreateModelRequestDTO createModelRequestDTO) {
        return createModelRequestDTO.getTableList().stream()
                .map(tableRequestDTO -> {
                    var attributeDTOList = AttributeDTOMapper.makeDTO(tableRequestDTO);
                    return makeDTO(tableRequestDTO, attributeDTOList);
                })
                .toList();
    }
}
