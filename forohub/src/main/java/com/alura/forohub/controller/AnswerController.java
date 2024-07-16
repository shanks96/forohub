package com.alura.forohub.controller;


import com.alura.forohub.dto.AnswerDTO;
import com.alura.forohub.infra.exception.DontExist;
import com.alura.forohub.models.answer.Answer;
import com.alura.forohub.models.answer.AnswerInfo;
import com.alura.forohub.models.answer.AnswerUpdateInfo;
import com.alura.forohub.service.AnswerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping(value = "/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping()
    public ResponseEntity<Page<AnswerDTO>> showAllAnswer(@PageableDefault(size = 3. sort = "fecha") Pageable page){
        var answer = answerService.getAllAnswer(page);
        return ResponseEntity.ok(answer);
    }

    @PostMapping()
    public ResponseEntity<AnswerDTO> registerAnswer(@RequestBody @Valid AnswerInfo answerInfo, UriComponentsBuilder uriComponentsBuilder) throws DontExist {
        var answer = answerService.registerAnswer(answerInfo);
        URI url = uriComponentsBuilder.path("/{id}/answer").buildAndExpand(answer.get()).toUri();
        return ResponseEntity.created(url).body(new AnswerDTO((Answer) answer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDTO> showAnswer(@PathVariable Long id) throws DontExist {
        var answer = answerService.getAnswerById(id);
        return ResponseEntity.ok(answer);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AnswerDTO> updateAnswer(@PathVariable Long id, @RequestBody AnswerUpdateInfo answerUpdateInfo) throws DontExist {
        var answer = answerService.updateAnswer(id, answerUpdateInfo);
        return ResponseEntity.ok(answer);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) throws DontExist {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
