package com.campus.course.file.dto.request;

import com.campus.course.file.entity.FileEntity;
import lombok.Getter;

@Getter
public class CreateFileRequest {
    private String title;
    private String description;
    private String link;
    private Long projectId;

    public FileEntity entity(){
        return FileEntity.builder()
                .title(title)
                .description(description)
                .link(link)
                .projectId(projectId)
                .build();
    }
}
