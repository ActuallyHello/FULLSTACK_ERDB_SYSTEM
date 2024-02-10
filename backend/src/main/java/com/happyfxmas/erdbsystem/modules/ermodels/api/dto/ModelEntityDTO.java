package com.happyfxmas.erdbsystem.modules.ermodels.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelEntityDTO {
    private Long id;
    private String title;
    private List<AttributeDTO> attributeDTOList;
}
