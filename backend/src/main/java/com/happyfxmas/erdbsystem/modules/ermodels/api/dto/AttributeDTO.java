package com.happyfxmas.erdbsystem.modules.ermodels.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttributeDTO {
    private Long id;
    private String title;
    private String attributeType;
}
