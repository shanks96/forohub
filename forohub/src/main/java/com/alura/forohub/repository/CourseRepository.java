package com.alura.forohub.repository;

import com.alura.forohub.models.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByActivoTrue(Pageable pageable);

    Optional<Course> findByIdAndActivoTrue(Long id);
}
