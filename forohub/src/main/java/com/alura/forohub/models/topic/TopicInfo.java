package com.alura.forohub.models.topic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TopicInfo(
        @NotBlank @Size(max = 50, min = 5) String titulo,
        @NotBlank @Size(max = 255, min =5 ) String mensaje,
        @NotNull @FutureOrPresent LocalDateTime fecha,
        @NotNull @Valid Long autor,
        @NotNull @Valid Long curso
        ) {
}
