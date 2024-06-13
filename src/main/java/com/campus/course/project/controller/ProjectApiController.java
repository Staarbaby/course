package com.campus.course.project.controller;

import com.campus.course.project.dto.request.CreateProjectRequest;
import com.campus.course.project.dto.request.EditProjectRequest;
import com.campus.course.project.dto.response.ProjectFullResponse;
import com.campus.course.project.dto.response.ProjectResponse;
import com.campus.course.project.entity.ProjectEntity;
import com.campus.course.project.exception.ProjectNotFoundException;
import com.campus.course.project.repository.ProjectRepository;
import com.campus.course.project.routes.ProjectRoutes;
import com.campus.course.file.entity.FileEntity;
import com.campus.course.file.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;



@RestController
@AllArgsConstructor
public class ProjectApiController {
    private final ProjectRepository projectRepository;
    private final FileRepository fileRepository;

    @PostMapping(ProjectRoutes.CREATE)
    public ProjectFullResponse create(@RequestBody CreateProjectRequest request){
        ProjectEntity entity = projectRepository.save(request.entity());
        return ProjectFullResponse.of(entity, fileRepository.findByProjectId(entity.getId()));
    }
    @GetMapping(ProjectRoutes.BY_ID)
    public ProjectFullResponse findById(@PathVariable Long id) throws ProjectNotFoundException {
        ProjectEntity entity = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
        return ProjectFullResponse.of(entity, fileRepository.findByProjectId(entity.getId()));
    }

    @PutMapping(ProjectRoutes.EDIT)
    public ProjectFullResponse edit(@PathVariable Long id, @RequestBody EditProjectRequest request) throws ProjectNotFoundException {
        ProjectEntity entity = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);

        entity.setDescription(request.getDescription());
        entity.setTitle(request.getTitle());

        entity = projectRepository.save(entity);

        return ProjectFullResponse.of(entity, fileRepository.findByProjectId(entity.getId()));
    }

    @GetMapping(ProjectRoutes.SEARCH)
    public List<ProjectResponse> search (@RequestParam(defaultValue = "") String query,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<ProjectEntity> example = Example.of(
                ProjectEntity.builder()
                        .title(query)
                        .description(query)
                        .build(),
                exampleMatcher);
        return projectRepository
                .findAll(example, pageable)
                .stream()
                .map(ProjectResponse::of)
                .collect(Collectors.toList());

    }

    @DeleteMapping(ProjectRoutes.BY_ID)
    public String delete(@PathVariable Long id){
        List<FileEntity> fileEntities = fileRepository.findByProjectId(id);
        for (FileEntity file : fileEntities)
            fileRepository.deleteById(file.getId());

        projectRepository.deleteById(id);
        return HttpStatus.OK.name();
    }

}
