package com.happyfxmas.erdbsystem.modules.persons.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupRequestDTO {
    @NotNull(message = "title must be not null!")
    @NotBlank(message = "title must be not empty!")
    private String title;
}
