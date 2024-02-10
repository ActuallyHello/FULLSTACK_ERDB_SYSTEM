package com.happyfxmas.erdbsystem.modules.persons.api.dto.response;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.PositionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TeacherWithPositionDTO {
    private Long id;
    private PositionDTO positionDTO;
}
