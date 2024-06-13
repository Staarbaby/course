package com.campus.course.file.controller;

import com.campus.course.project.entity.ProjectEntity;
import com.campus.course.project.exception.ProjectNotFoundException;
import com.campus.course.project.repository.ProjectRepository;
import com.campus.course.file.dto.request.CreateFileRequest;
import com.campus.course.file.dto.request.EditFileRequest;
import com.campus.course.file.dto.response.FileFullResponse;
import com.campus.course.file.dto.response.FileResponse;
import com.campus.course.file.entity.FileEntity;
import com.campus.course.file.exception.FileNotFoundException;
import com.campus.course.file.repository.FileRepository;
import com.campus.course.file.routes.FileRoutes;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class FileApiController {
    private final FileRepository fileRepository;
    private final ProjectRepository projectRepository;


    @PostMapping(FileRoutes.CREATE)
    public FileFullResponse create(@RequestBody CreateFileRequest request) throws ProjectNotFoundException {
        ProjectEntity course = projectRepository
                .findById(request.getProjectId())
                .orElseThrow(ProjectNotFoundException::new);

        FileEntity entity = fileRepository.save(request.entity());
        return FileFullResponse.of(entity,course);
    }
    @GetMapping(FileRoutes.BY_ID)
    public FileFullResponse byId(@PathVariable Long id) throws ProjectNotFoundException, FileNotFoundException {
        FileEntity file = fileRepository
                .findById(id)
                .orElseThrow(FileNotFoundException::new);

        ProjectEntity course = file.getProjectId() != null ?
                projectRepository.findById(file.getProjectId()).orElseThrow(ProjectNotFoundException::new)
                : null;

        return FileFullResponse.of(file, course);
    }

    @PutMapping(FileRoutes.EDIT)
    public FileFullResponse edit(@PathVariable Long id, @RequestBody EditFileRequest request) throws FileNotFoundException, ProjectNotFoundException {
        FileEntity file = fileRepository.findById(id).orElseThrow(FileNotFoundException::new);

        ProjectEntity course = file.getProjectId() != null ?
                projectRepository.findById(file.getProjectId()).orElseThrow(ProjectNotFoundException::new)
                : null;

        file.setTitle(request.getTitle());
        file.setDescription(request.getDescription());
        file.setLink(request.getLink());

        file = fileRepository.save(file);

        return FileFullResponse.of(file, course);
    }

    @GetMapping(FileRoutes.SEARCH)
    public List<FileResponse> search(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        ExampleMatcher ignoringExampleMather = ExampleMatcher.matchingAny()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<FileEntity> example = Example.of(
                FileEntity.builder().title(query).description(query).build(),
                ignoringExampleMather);

        return fileRepository
                .findAll(example, pageable)
                .stream()
                .map(FileResponse::of)
                .collect(Collectors.toList());

    }

    @DeleteMapping(FileRoutes.BY_ID)
    public String delete(@PathVariable Long id){
        fileRepository.deleteById(id);
        return HttpStatus.OK.name();
    }

}
