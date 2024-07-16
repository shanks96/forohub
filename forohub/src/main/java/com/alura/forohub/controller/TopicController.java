package com.alura.forohub.controller;

import com.alura.forohub.dto.TopicDTO;
import com.alura.forohub.infra.exception.DontExist;
import com.alura.forohub.models.topic.TopicInfo;
import com.alura.forohub.models.topic.TopicUpdateInfo;
import com.alura.forohub.service.TopicService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping(value = "/topic")
//@SecurityRequirement(name = "bearer-key")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping()
    public ResponseEntity<Page<TopicDTO>> showAllTopics(@PageableDefault(size = 3, sort = "titulo")Pageable page){
        var topics = topicService.getAllTopics(page);
        return ResponseEntity.ok(topics);
    }

    @PostMapping()
    public  ResponseEntity<TopicDTO> registerTopic(@RequestBody @Valid TopicInfo topicInfo, UriComponentsBuilder uriComponentsBuilder) throws DontExist {
        var topico = topicService.registerTopic(topicInfo);
        URI url = uriComponentsBuilder.path("/topic/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(new TopicDTO(topico));
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> showTopico(@PathVariable Long id) throws NoExiste {
        var topico = topicService.getTopicoById(id);
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicDTO> updateTopico(@PathVariable Long id, @RequestBody TopicUpdateInfo topicUpdateInfo) throws NoExiste{
        var topico = topicService.updateTopic(id,topicUpdateInfo);
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) throws DontExist {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
