package com.alura.forohub.repository;

import com.alura.forohub.models.topic.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findByStatusTrue(Pageable pageable);

    Optional<Topic> findByIdAndStatusTrue(Long id);

}
