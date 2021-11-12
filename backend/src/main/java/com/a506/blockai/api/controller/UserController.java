package com.a506.blockai.api.controller;

import com.a506.blockai.api.dto.request.LoginRequest;
import com.a506.blockai.api.dto.request.SignupRequest;
import com.a506.blockai.api.service.UserService;
import com.a506.blockai.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * Created by Yeseul Kim on 2021-11-11
 *
 * 유저 관련 Controller
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /** 회원가입 */
    @PostMapping("/sign-up")
    public ResponseEntity signup (@RequestBody SignupRequest signupRequest) throws ParseException {
        userService.register(signupRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    /** 로그인 */
    @PostMapping("/sign-in")
    public ResponseEntity<String> signin (@RequestBody LoginRequest loginRequest) {
        String jwt = userService.login(loginRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtTokenProvider.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity(jwt, httpHeaders, HttpStatus.OK);
    }
}