package com.alura.forohub.models.answer;

import com.alura.forohub.models.topic.Topic;
import com.alura.forohub.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne
    private Topic topic;
    private LocalDateTime fecha;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private User user;
    private String solucion;
    private boolean activo;

    public Answer(AnswerInfo answerInfo, Topic topic, User autor){
        this.mensaje = answerInfo.mensaje();
        this.topic = topic;
        this.fecha = answerInfo.fecha();
        this.user = autor;
        this.solucion = answerInfo.solucion();
        this.activo = true;
    }


    public void answerUpdate(AnswerUpdateInfo answerUpdateInfo){
        if(answerUpdateInfo.mensaje() != null){
            this.mensaje = answerUpdateInfo.mensaje();
        }
        if (answerUpdateInfo.solucion() !=  null){
            this.solucion = answerUpdateInfo.solucion();
        }
    }

    public  void deactivate(){
        this.activo = false;
    }

    public Long getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Topic getTopic() {
        return topic;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public User getUser() {
        return user;
    }

    public String getSolucion() {
        return solucion;
    }

    public boolean isActivo() {
        return activo;
    }
}
