package com.alura.forohub.models.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserInfo(
        @NotBlank String nombre,
        @NotBlank String email,
        @NotNull Perfil perfil,
        @NotBlank @Size(min = 5) String password
) {
}
