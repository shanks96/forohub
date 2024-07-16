package com.alura.forohub.models.topic;

import com.alura.forohub.models.course.CourseUpdateInfo;

public record TopicUpdateInfo(
        String titulo,
        String mensaje,
        CourseUpdateInfo curso
) {
}
