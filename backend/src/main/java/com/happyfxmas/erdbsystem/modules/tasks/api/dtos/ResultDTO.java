package com.happyfxmas.erdbsystem.modules.tasks.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.enums.Mark;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResultDTO {
    private Long id;
    private Mark mark;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant updatedAt;
}
