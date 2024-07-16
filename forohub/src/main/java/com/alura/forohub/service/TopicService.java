package com.alura.forohub.service;

import com.alura.forohub.dto.TopicDTO;
import com.alura.forohub.infra.exception.DontExist;
import com.alura.forohub.models.topic.Topic;
import com.alura.forohub.models.topic.TopicInfo;
import com.alura.forohub.models.topic.TopicUpdateInfo;
import com.alura.forohub.repository.CourseRepository;
import com.alura.forohub.repository.TopicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Page<TopicDTO> getAllTopics(Pageable page){
        var datos = topicRepository.findByStatusTrue(page);
        return datos.map(d -> new TopicDTO(d));
    }

    public Topic registerTopic(@Valid TopicInfo topicInfo) throws DontExist {
        var user = userRepository.findById(topicInfo.autor());
        if(!user.isPresent()){
            throw new DontExist("No existe esse usuario");
        }
        var curso = courseRepository.findById(topicInfo.curso());
        if(!curso.isPresent()){
            throw new DontExist("No existe este curso");
        }
        var topico = new Topic(topicInfo, user.get(),curso.get());
        topicRepository.save(topico);
        return topico;
    }

    public TopicDTO getTopicById(Long id) throws DontExist {
        var topico = existe(id);
        return new TopicDTO(topico);
    }

    public TopicDTO updateTopic(Long id, TopicUpdateInfo datosTopico) throws DontExist {
        var topico = existe(id);
        topico.topicUpdate(datosTopico);
        return new TopicDTO(topico);
    }

    public void deleteTopic(Long id) throws DontExist {
        var topico = existe(id);
        topico.deactivate();
    }

    private Topic existe(Long id) throws DontExist {
        var topico = topicRepository.findByIdAndStatusTrue(id);
        if(!topico.isPresent())
            throw new DontExist("No existe esse topico");
        return topico.get();
    }
}
