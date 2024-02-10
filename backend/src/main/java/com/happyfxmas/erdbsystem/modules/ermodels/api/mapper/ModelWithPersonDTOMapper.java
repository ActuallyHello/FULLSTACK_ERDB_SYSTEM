package com.happyfxmas.erdbsystem.modules.ermodels.api.mapper;


import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.response.ModelWithPersonDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.PersonDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import lombok.NonNull;

import java.util.List;

public class ModelWithPersonDTOMapper {
    public static ModelWithPersonDTO makeDTO(Model model, PersonDTO personDTO) {
        return ModelWithPersonDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .topic(model.getTopic())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .isTaskResult(model.getIsTaskResult())
                .personDTO(personDTO)
                .build();
    }

    public static List<ModelWithPersonDTO> makeDTOs(@NonNull List<Model> modelList) {
        return modelList.stream()
                .map(model -> ModelWithPersonDTOMapper.makeDTO(
                        model,
                        PersonDTOMapper.makeDTO(model.getPerson())))
                .toList();
    }
}
