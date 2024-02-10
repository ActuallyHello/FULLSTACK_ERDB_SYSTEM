package com.happyfxmas.erdbsystem.modules.persons.api.mapper;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.PositionDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.request.CreatePositionRequestDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Position;
import lombok.NonNull;

import java.util.List;

public class PositionDTOMapper {

    public static PositionDTO makeDTO(@NonNull Position position) {
        return PositionDTO.builder()
                .id(position.getId())
                .title(position.getTitle())
                .build();
    }

    public static PositionDTO makeDTO(@NonNull CreatePositionRequestDTO createPositionRequestDTO) {
        return PositionDTO.builder()
                .title(createPositionRequestDTO.getTitle())
                .build();
    }

    public static Position fromDTO(@NonNull PositionDTO positionDTO) {
        return Position.builder()
                .id(positionDTO.getId())
                .title(positionDTO.getTitle())
                .build();
    }

    public static Position fromDTO(@NonNull CreatePositionRequestDTO createPositionRequestDTO) {
        return Position.builder()
                .title(createPositionRequestDTO.getTitle())
                .build();
    }

    public static List<PositionDTO> makeDTOs(List<Position> positionList) {
        return positionList.stream()
                .map(PositionDTOMapper::makeDTO)
                .toList();
    }
}
