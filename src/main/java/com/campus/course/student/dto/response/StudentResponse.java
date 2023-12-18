package com.campus.course.student.dto.response;

import com.campus.course.student.entity.StudentEntity;
import com.campus.course.student.repository.StudentRepository;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentResponse {
    protected Long id;
    protected String firstname;
    protected String lastname;
    protected String email;

    public static StudentResponse of(StudentEntity entity){
        return StudentResponse.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .build();
    }
}
