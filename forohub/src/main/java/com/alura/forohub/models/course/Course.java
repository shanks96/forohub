package com.alura.forohub.models.course;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private boolean activo;

    public Course(CourseInfo courseInfo){
        this.nombre = courseInfo.nombre();
        this.categoria = courseInfo.categoria();
        this.activo = true;
    }

    public void CourseUpdate(CourseUpdateInfo courseUpdateInfo){
        if(courseUpdateInfo.nombre() != null){
            this.nombre = courseUpdateInfo.nombre();
        }
        if(courseUpdateInfo.categoria() != null){
            this.categoria = courseUpdateInfo.categoria();
        }
    }

    public void deactivate(){
        this.activo = false;
    }
}
