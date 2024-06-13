package com.campus.course.user.dto.response;

import com.campus.course.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    protected Long id;
    protected String firstname;
    protected String lastname;
    protected String email;

    public static UserResponse of(UserEntity entity){
        return UserResponse.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .build();
    }
}
