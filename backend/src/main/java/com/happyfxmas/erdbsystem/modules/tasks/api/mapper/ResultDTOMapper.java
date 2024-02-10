package com.happyfxmas.erdbsystem.modules.tasks.api.mapper;


import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.ResultDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.request.CreateResultRequestDTO;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Result;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.enums.Mark;

public class ResultDTOMapper {

    public static ResultDTO makeDTO(Result result) {
        return ResultDTO.builder()
                .id(result.getId())
                .mark(result.getMark())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .build();
    }

    public static ResultDTO makeDTO(CreateResultRequestDTO createResultRequestDTO) {
        return ResultDTO.builder()
                .mark(Mark.fromInteger(createResultRequestDTO.getMark()))
                .build();
    }

    public static Result fromDTO(ResultDTO resultDTO) {
        return Result.builder()
                .mark(resultDTO.getMark())
                .build();
    }
}
