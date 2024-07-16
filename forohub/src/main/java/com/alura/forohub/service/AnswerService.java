package com.alura.forohub.service;

import com.alura.forohub.dto.AnswerDTO;
import com.alura.forohub.infra.exception.DontExist;
import com.alura.forohub.models.answer.Answer;
import com.alura.forohub.models.answer.AnswerInfo;
import com.alura.forohub.models.answer.AnswerUpdateInfo;
import com.alura.forohub.repository.AnswerRepository;
import com.alura.forohub.repository.TopicRepository;
import com.alura.forohub.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    public Page<AnswerDTO> getAllAnswer(Pageable page){
        var answer = answerRepository.findByIdActivoTrue(page);
        return answer.map(AnswerDTO::new);
    }

    public Page<AnswerDTO> registerAnswer(@Valid AnswerInfo answerInfo) throws DontExist {
        var user = userRepository.findByIdAndActivoTrue(answerInfo.autor());
        if (!user.isPresent()){
            throw new DontExist("Usuario no registrado");
        }
        var topic = topicRepository.findByIdAndStatusTrue(answerInfo.topic());
        if (!topic.isPresent()){
            throw new DontExist("El Tema no existe");
        }
        var answer = new Answer(answerInfo, topic.get(), user.get());
        answerRepository.save(answer);
        return answer;
    }

    public AnswerDTO getAnswerById(Long id) throws DontExist {
        var answer = existe(id);
        return new AnswerDTO(answer);
    }

    public AnswerDTO updateAnswer(Long id, AnswerUpdateInfo answerUpdateInfo) throws DontExist {
        var answer = existe(id);
        answer.answerUpdate(answerUpdateInfo);
        return new AnswerDTO(answer);
    }

    public void deleteAnswer(Long id) throws DontExist {
        var answer = existe(id);
        answer.deactivate();
    }

    private Answer existe(Long id) throws DontExist {
        var answer = answerRepository.findByIdActivoTrue(id);
        if (!answer.isPresent()){
            throw new DontExist("No encontramos la respuesta buscada");
        }
        return answer.get();
    }
}
