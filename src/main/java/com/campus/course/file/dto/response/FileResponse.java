package com.campus.course.file.dto.response;

import com.campus.course.file.entity.FileEntity;
import com.campus.course.file.repository.FileRepository;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileResponse {
    protected Long id;
    protected String title;
    protected String description;
    protected String link;
    protected Long projectId;

    public static FileResponse of(FileEntity file){
        return FileResponse.builder()
                .id(file.getId())
                .title(file.getTitle())
                .description(file.getDescription())
                .link(file.getLink())
                .projectId(file.getProjectId())
                .build();
    }
}
