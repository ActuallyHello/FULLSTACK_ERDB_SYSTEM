package com.happyfxmas.erdbsystem.modules.ermodels.api.mapper;

import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.AttributeDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.request.TableRequestDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Attribute;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.enums.AttributeType;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AttributeDTOMapper {
    public static AttributeDTO makeDTO(@NonNull Attribute attribute) {
        return AttributeDTO.builder()
                .id(attribute.getId())
                .title(attribute.getTitle())
                .attributeType(attribute.getAttributeType().getValue())
                .build();
    }

    public static List<AttributeDTO> makeDTO(@NonNull TableRequestDTO tableRequestDTO) {
        List<AttributeDTO> attributeDTOList = new ArrayList<>();
        tableRequestDTO.getPkFields().forEach(attribute -> {
            attributeDTOList.add(AttributeDTO.builder()
                    .title(attribute)
                    .attributeType(AttributeType.PRIMARY_KEY.getValue())
                    .build());
        });
        tableRequestDTO.getFkFields().forEach(attribute -> {
            attributeDTOList.add(AttributeDTO.builder()
                    .title(attribute)
                    .attributeType(AttributeType.FOREIGN_KEY.getValue())
                    .build());
        });
        tableRequestDTO.getAttrFields().forEach(attribute -> {
            attributeDTOList.add(AttributeDTO.builder()
                    .title(attribute)
                    .attributeType(AttributeType.ATTRIBUTE.getValue())
                    .build());
        });
        return attributeDTOList;
    }
}
