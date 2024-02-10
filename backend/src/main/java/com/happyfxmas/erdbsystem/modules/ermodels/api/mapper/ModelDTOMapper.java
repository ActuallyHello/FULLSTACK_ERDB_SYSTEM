package com.happyfxmas.erdbsystem.modules.ermodels.api.mapper;


import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.ModelDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.request.CreateModelRequestDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import lombok.NonNull;

public class ModelDTOMapper {
    public static ModelDTO makeDTO(@NonNull Model model) {
        return ModelDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .topic(model.getTopic())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .isTaskResult(model.getIsTaskResult())
                .build();
    }

    public static ModelDTO makeDTO(@NonNull CreateModelRequestDTO createModelRequestDTO) {
        return ModelDTO.builder()
                .title(createModelRequestDTO.getTitle())
                .description(createModelRequestDTO.getDescription())
                .topic(createModelRequestDTO.getTopic())
                .build();
    }

    public static Model fromDTO(@NonNull CreateModelRequestDTO createModelRequestDTO) {
        return Model.builder()
                .title(createModelRequestDTO.getTitle())
                .description(createModelRequestDTO.getDescription())
                .topic(createModelRequestDTO.getTopic())
                .build();
    }

    public static Model fromDTO(@NonNull ModelDTO modelDTO) {
        return Model.builder()
                .id(modelDTO.getId())
                .title(modelDTO.getTitle())
                .description(modelDTO.getDescription())
                .topic(modelDTO.getTopic())
                .isTaskResult(modelDTO.getIsTaskResult())
                .build();
    }
}
