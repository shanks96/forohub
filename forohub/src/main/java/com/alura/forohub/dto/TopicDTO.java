package com.alura.forohub.dto;

import com.alura.forohub.models.topic.Topic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record TopicDTO(
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        UserDTO autor,
        CourseDTO curso,
        List<AnswerDTO> respuestas
) {
    public TopicDTO(Topic t){
        this(t.getTitulo(),
                t.getMensaje(),
                t.getFecha(),
                new UserDTO(t.getAutor()),
                new CourseDTO(t.getCurso()),
                t.getRespuestas().stream().map(AnswerDTO::new).toList());
    }
}
