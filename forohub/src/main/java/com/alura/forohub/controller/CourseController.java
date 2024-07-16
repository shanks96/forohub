package com.alura.forohub.controller;

import com.alura.forohub.dto.CourseDTO;
import com.alura.forohub.infra.exception.DontExist;
import com.alura.forohub.models.course.CourseInfo;
import com.alura.forohub.models.course.CourseUpdateInfo;
import com.alura.forohub.service.CourseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Controller
@RequestMapping(value = "/curso")
@SecurityRequirement(name = "bearer-key")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping()
    public ResponseEntity<Page<CourseDTO>> showAllCourses(
            @PageableDefault(size = 3, sort = "titulo")Pageable page){
        var cursos = courseService.getAllCourses(page);
        return ResponseEntity.ok(cursos);

    }

    @PostMapping()
    public ResponseEntity<CourseDTO> registerCourse(@RequestBody @Valid CourseInfo courseInfo, UriComponentsBuilder uriComponentsBuilder){
        var curso = courseService.courseRegister(courseInfo);
        URI url = uriComponentsBuilder.path("/curso/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(url).body(new CourseDTO(curso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> showCourse(@PathVariable Long id) throws DontExist {
        var curso = courseService.getCourseById(id);
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateInfo courseUpdateInfo) throws DontExist {
        var curso = courseService.updatecourse(id, courseUpdateInfo);
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<void> deleteCourse(@PathVariable Long id) throws DontExist {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }




}
