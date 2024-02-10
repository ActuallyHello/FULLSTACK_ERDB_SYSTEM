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
public class RelationDTO {
    private Long id;
    private String fromEntity;
    private String power;
    private String toEntity;
}
