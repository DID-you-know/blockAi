package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.LoginRequest;
import com.a506.blockai.api.dto.request.SignupRequest;
import com.a506.blockai.api.dto.response.LoginResponse;
import com.a506.blockai.db.entity.Role;
import com.a506.blockai.db.entity.User;
import com.a506.blockai.db.repository.UserRepository;
import com.a506.blockai.exception.EmailDuplicatedException;
import com.a506.blockai.exception.UserNotFoundException;
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
import java.util.Optional;

/**
 * Created by Yeseul Kim on 2021-11-11
 * <p>
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

    public LoginResponse login(final LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }

        LoginResponse loginResponse = new LoginResponse(jwtTokenProvider.createToken(authentication), user.getId(), user.getName(), user.getDid() == null ? null : user.getDid().getIssuedAt());
        return loginResponse;
    }

    public int getUserIdByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhone(phoneNumber);
        return user == null ? -1 : user.getId();
    }
}