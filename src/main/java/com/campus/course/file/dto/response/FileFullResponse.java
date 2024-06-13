package com.campus.course.file.dto.response;

import com.campus.course.project.dto.response.ProjectResponse;
import com.campus.course.project.entity.ProjectEntity;
import com.campus.course.file.entity.FileEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileFullResponse {
    protected Long id;
    protected String title;
    protected String description;
    protected String link;
    protected Long projectId;
    protected ProjectResponse project;

    public static FileFullResponse of(FileEntity file, ProjectEntity project){
        return FileFullResponse.builder()
                .id(file.getId())
                .title(file.getTitle())
                .description(file.getDescription())
                .link(file.getLink())
                .projectId(file.getProjectId())
                .project(ProjectResponse.of(project))
                .build();
    }
}
