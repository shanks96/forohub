package com.alura.forohub.models.topic;

import com.alura.forohub.models.answer.Answer;
import com.alura.forohub.models.user.User;
import com.alura.forohub.models.course.Course;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topic", uniqueConstraints = {@UniqueConstraint(columnNames = {"titulo","mensaje"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha;
    private Boolean status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User autor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Course curso;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answer> respuestas;

    public Topic(TopicInfo topicInfo, User user, Course course){
        this.titulo = topicInfo.titulo();
        this.mensaje = topicInfo.mensaje();
        this.fecha = topicInfo.fecha();
        this.autor = user;
        this.curso = course;
        this.status = true;
        this.respuestas = new ArrayList<>();
    }

    public Topic(TopicInfo topicInfo, User user, Course course, ArrayList<Answer> answers){
        this.titulo = topicInfo.titulo();
        this.mensaje = topicInfo.mensaje();
        this.fecha = topicInfo.fecha();
        this.autor = user;
        this.curso = course;
        this.status = true;
        this.respuestas = answers;
        for (Answer answer : this.respuestas) {
            answer.setTopic(this);
        }
    }

    public void topicUpdate(TopicUpdateInfo topicUpdateInfo){
        if(topicUpdateInfo.curso() != null){
            this.curso.CourseUpdate(topicUpdateInfo.curso());
        }
        if(topicUpdateInfo.mensaje() != null){
            this.mensaje = topicUpdateInfo.mensaje();
        }
        if(topicUpdateInfo.titulo() != null){
            this.titulo = topicUpdateInfo.titulo();
        }
    }

    public void deactivate(){
        this.status = false;
    }
}
