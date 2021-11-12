package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.LoginRequest;
import com.a506.blockai.api.dto.request.SignupRequest;
import com.a506.blockai.db.entity.Role;
import com.a506.blockai.db.entity.User;
import com.a506.blockai.db.repository.UserRepository;
import com.a506.blockai.exception.EmailDuplicatedException;
import com.a506.blockai.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Created by Yeseul Kim on 2021-11-11
 *
 * 유저 관련 Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public User register(final SignupRequest signupRequest) {
        Boolean existed = userRepository.existsByEmail(signupRequest.getEmail());

        if (existed) {
            throw new EmailDuplicatedException();
        }

        return userRepository.save(User.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .birth(signupRequest.getBirth())
                .phone(signupRequest.getPhone())
                .roles(Collections.singleton(Role.USER))
                .build());
    }

    public String login(final LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.createToken(authentication);
    }
}