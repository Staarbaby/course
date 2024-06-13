package com.campus.course.project.dto.response;

import com.campus.course.project.entity.ProjectEntity;
import com.campus.course.file.dto.response.FileResponse;
import com.campus.course.file.entity.FileEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@SuperBuilder
public class ProjectFullResponse extends ProjectEntity {
    private List<FileResponse> file = new ArrayList<>();
    public static ProjectFullResponse of(ProjectEntity entity, List<FileEntity> fileEntities){
        return ProjectFullResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .file(fileEntities.stream().map(FileResponse::of).collect(Collectors.toList()))
                .build();
    }
}
