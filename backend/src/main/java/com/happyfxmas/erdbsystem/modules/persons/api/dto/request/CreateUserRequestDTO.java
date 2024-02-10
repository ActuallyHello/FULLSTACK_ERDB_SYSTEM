package com.happyfxmas.erdbsystem.modules.persons.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {
    @NotNull(message = "login must be not null!")
    @NotBlank(message = "login must be not empty!")
    private String login;

    @NotNull(message = "password must be not null!")
    @NotBlank(message = "password must be not empty!")
    @Size(min = 5, message = "password must be more than 5 symbols")
    @Size(max = 20, message = "password must be less than 20 symbols")
    private String password;

    @NotNull(message = "email must be not null!")
    @Email(message = "should be provided a valid email!")
    private String email;
}
