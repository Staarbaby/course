package com.campus.course.user.service;

import com.campus.course.user.entity.UserEntity;
import com.campus.course.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException{
        Optional<UserEntity> studentEntityOptional = userRepository.findByEmail(email);
        if (studentEntityOptional.isEmpty()) throw new UsernameNotFoundException("Student not exit");

        UserEntity student = studentEntityOptional.get();
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("student"));
        return new User(student.getEmail(), student.getPassword(), authorities);
    }
}
