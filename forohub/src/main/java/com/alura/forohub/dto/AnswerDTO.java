package com.alura.forohub.dto;

import com.alura.forohub.models.answer.Answer;

import java.time.LocalDateTime;
import lombok.*;


public record AnswerDTO(
        String mensaje,
        LocalDateTime fecha,
        UserDTO autor,
        String solucion
) {
    public AnswerDTO(Answer r){
        this(r.getMensaje(), r.getFecha(), new UserDTO(r.getUser()), r.getSolucion());
    }
}