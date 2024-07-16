package com.alura.forohub.service;

import com.alura.forohub.dto.CourseDTO;
import com.alura.forohub.infra.exception.DontExist;
import com.alura.forohub.models.course.Course;
import com.alura.forohub.models.course.CourseInfo;
import com.alura.forohub.models.course.CourseUpdateInfo;
import com.alura.forohub.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Page<CourseDTO> getAllCourses(Pageable page){
        var respuesta = courseRepository.findByActivoTrue(page);
        return respuesta.map(CourseDTO::new);
    }

    public Course courseRegister(@Valid CourseInfo courseInfo){
        var curso = new Course(courseInfo);
        courseRepository.save(curso);
        return curso;
    }

    public CourseDTO getCourseById(Long id) throws DontExist {
        var curso = existe(id);
        return new CourseDTO(curso);
    }

    public CourseDTO updatecourse(Long id, CourseUpdateInfo courseUpdateInfo) throws DontExist {
        var curso = existe(id);
        curso.CourseUpdate(courseUpdateInfo);
        return new CourseDTO(curso);
    }

    public void deleteCourse(Long id) throws DontExist {
        var curso = existe(id);
        curso.deactivate();
    }

    private Course existe(Long id) throws DontExist {
        var curso = courseRepository.findByActivoTrue(id);
        if (!curso.isPresent()){
            throw new DontExist("No existe el curso con el id dado");
        }
        return curso.get();
    }
}
