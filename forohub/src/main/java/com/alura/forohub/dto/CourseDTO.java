package com.alura.forohub.dto;

import com.alura.forohub.models.course.Categoria;
import com.alura.forohub.models.course.Course;

public record CourseDTO(
        String nombre,
        Categoria categoria
) {
    public CourseDTO(Course course){
        this(course.getNombre(), course.getCategoria());
    }
}
