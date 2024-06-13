package com.campus.course.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditUserRequest {
    private String firstname;
    private String lastname;
}
