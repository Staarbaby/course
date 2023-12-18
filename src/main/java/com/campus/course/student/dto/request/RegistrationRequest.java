package com.campus.course.student.dto.request;

import com.campus.course.student.exception.BedRequestException;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegistrationRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public void validate() throws BedRequestException {
        if (email == null || email.isBlank()) throw new BedRequestException();
        if (password == null || password.isBlank()) throw new BedRequestException();

    }
}
