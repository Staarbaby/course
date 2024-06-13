package com.campus.course.project.dto.request;

import com.campus.course.project.entity.ProjectEntity;
import lombok.Data;

@Data
public class CreateProjectRequest {
    private String title;
    private String description;
    public ProjectEntity entity(){
        return ProjectEntity.builder()
                .title(title)
                .description(description)
                .build();
    }
}
