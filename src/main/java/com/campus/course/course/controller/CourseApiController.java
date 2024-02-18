package com.campus.course.course.controller;

import com.campus.course.course.dto.request.CreateCourseRequest;
import com.campus.course.course.dto.request.EditCourseRequest;
import com.campus.course.course.dto.response.CourseResponse;
import com.campus.course.course.entity.CourseEntity;
import com.campus.course.course.exception.CourseNotFoundException;
import com.campus.course.course.repository.CourseRepository;
import com.campus.course.course.routes.CourseRoutes;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CourseApiController {
    private final CourseRepository courseRepository;

    @PostMapping(CourseRoutes.CREATE)
    public CourseResponse create(@RequestBody CreateCourseRequest request){
        CourseEntity entity = courseRepository.save(request.entity());
        return CourseResponse.of(entity);
    }
    @GetMapping(CourseRoutes.BY_ID)
    public CourseResponse findById(@PathVariable Long id) throws CourseNotFoundException{
        CourseEntity entity = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
        return CourseResponse.of(entity);
    }

    @PutMapping(CourseRoutes.EDIT)
    public CourseResponse edit(@PathVariable Long id, @RequestBody EditCourseRequest request) throws CourseNotFoundException{
        CourseEntity entity = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);

        entity.setDescription(request.getDescription());
        entity.setTitle(request.getTitle());

        entity = courseRepository.save(entity);

        return CourseResponse.of(entity);
    }

    @DeleteMapping(CourseRoutes.BY_ID)
    public String delete(@PathVariable Long id){
        courseRepository.deleteById(id);
        return HttpStatus.OK.name();
    }


}
