package com.campus.course.project.dto.request;

import lombok.Data;

@Data
public class EditProjectRequest {
    private Long id;
    private String title;
    private String description;
}
