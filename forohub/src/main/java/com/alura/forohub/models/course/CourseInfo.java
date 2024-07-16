package com.alura.forohub.models.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseInfo(
        @NotBlank String nombre,
        @NotNull Categoria categoria
) {
}
