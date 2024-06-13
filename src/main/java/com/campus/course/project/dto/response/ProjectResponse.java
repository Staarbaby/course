package com.campus.course.project.dto.response;

import com.campus.course.project.entity.ProjectEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ProjectResponse extends ProjectEntity {
    public static ProjectResponse of(ProjectEntity entity){
        return ProjectResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .build();
    }
}
