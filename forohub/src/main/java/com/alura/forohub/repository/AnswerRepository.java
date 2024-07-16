package com.alura.forohub.repository;

import com.alura.forohub.models.answer.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findByActivoTrue(Pageable page);

    Optional<Answer> findByIdActivoTrue(Long id);
}
