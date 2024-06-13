package com.campus.course.file.dto.request;

import lombok.Getter;

@Getter
public class EditFileRequest {
    private Long id;
    private String title;
    private String description;
    private String link;

}
