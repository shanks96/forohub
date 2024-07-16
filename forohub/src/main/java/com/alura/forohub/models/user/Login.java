package com.alura.forohub.models.user;

import jakarta.validation.constraints.NotBlank;

public record Login(
        @NotBlank String email,
        @NotBlank String password

) {
}
