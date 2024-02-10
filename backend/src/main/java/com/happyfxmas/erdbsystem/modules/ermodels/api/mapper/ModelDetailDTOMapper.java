package com.happyfxmas.erdbsystem.modules.ermodels.api.mapper;

import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.ModelEntityDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.RelationDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.response.ModelDetailDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.PersonDTO;
import lombok.NonNull;

import java.util.List;

public class ModelDetailDTOMapper {
    public static ModelDetailDTO makeDTO(@NonNull Model model,
                                         @NonNull PersonDTO personDTO,
                                         @NonNull List<ModelEntityDTO> modelEntityDTOList,
                                         @NonNull List<RelationDTO> relationDTOList) {
        return ModelDetailDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .topic(model.getTopic())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .isTaskResult(model.getIsTaskResult())
                .personDTO(personDTO)
                .modelEntityDTOList(modelEntityDTOList)
                .relationDTOList(relationDTOList)
                .build();
    }
}
