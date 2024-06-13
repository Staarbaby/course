package com.campus.course.user.controller;

import com.campus.course.user.dto.request.EditUserRequest;
import com.campus.course.user.dto.request.RegistrationRequest;
import com.campus.course.user.dto.response.UserResponse;
import com.campus.course.user.entity.UserEntity;
import com.campus.course.base.exception.BedRequestException;
import com.campus.course.user.exception.UserAlreadyExistException;
import com.campus.course.user.exception.UserNotFoundException;
import com.campus.course.user.repository.UserRepository;
import com.campus.course.user.routes.UserRoutes;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserApiController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(UserRoutes.REGISTRATION)
    public UserResponse registration(@RequestBody RegistrationRequest request) throws BedRequestException, UserAlreadyExistException {
        request.validate();

        Optional<UserEntity> check = userRepository.findByEmail(request.getEmail());
        if (check.isPresent()) throw new UserAlreadyExistException();

        UserEntity student = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        student = userRepository.save(student);
        return UserResponse.of(student);
    }

    @PutMapping(UserRoutes.EDIT)
    public UserResponse edit(Principal principal, @RequestBody EditUserRequest edit) throws UserNotFoundException {
        UserEntity student = userRepository
                .findByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        student.setFirstname(edit.getFirstname());
        student.setLastname(edit.getLastname());

        userRepository.save(student);

        return UserResponse.of(student);
    }

    @GetMapping(UserRoutes.BY_ID)
    public UserResponse findById(@PathVariable Long id) throws UserNotFoundException {
        UserEntity student = userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
        return UserResponse.of(student);
    }

    @GetMapping(UserRoutes.SEARCH)
    public List<UserResponse> search(@RequestParam(defaultValue = "") String query,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("firstname", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("lastname", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<UserEntity> example = Example.of(
                UserEntity.builder()
                        .lastname(query)
                        .firstname(query)
                        .email(query)
                        .build(),
                exampleMatcher);
        return userRepository
                .findAll(example, pageable)
                .stream()
                .map(UserResponse::of)
                .collect(Collectors.toList());

    }
}
