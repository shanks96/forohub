package com.alura.forohub.models.answer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AnswerInfo(
        @NotBlank String mensaje,
        @NotNull @Valid Long topic,
        @NotNull @FutureOrPresent LocalDateTime fecha,
        @NotNull @Valid Long autor,
        @NotBlank String solucion
        ) {
}
