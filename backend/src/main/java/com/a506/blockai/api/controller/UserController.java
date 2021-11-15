package com.a506.blockai.api.controller;

import com.a506.blockai.api.dto.request.LoginRequest;
import com.a506.blockai.api.dto.request.SignupRequest;
import com.a506.blockai.api.dto.response.LoginResponse;
import com.a506.blockai.api.service.UserService;
import com.a506.blockai.jwt.JwtTokenProvider;
import com.a506.blockai.api.dto.request.SendSmsRequest;
import com.a506.blockai.api.service.SmsService;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    @Autowired
    private final SmsService smsService;

    /** 회원가입 */
    @PostMapping("/sign-up")
    @ApiOperation(value = "회원가입", notes = "<strong>이메일, 패스워드, 이름, 생일, 전화번호</strong>을 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 성공"),
    })
    public ResponseEntity signup (@RequestBody SignupRequest signupRequest) throws ParseException {
        userService.register(signupRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    /** 로그인 */
    @PostMapping("/sign-in")
    @ApiOperation(value = "로그인", notes = "<strong>이메일과 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공(헤더에도 토근 있음)", response = LoginResponse.class),
            @ApiResponse(code = 401, message = "인증 실패(이메일 존재X or 비밀번호 불일치)")
    })
    public ResponseEntity<LoginResponse> signin (@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);
        String jwt = loginResponse.getToken();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtTokenProvider.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity(loginResponse, httpHeaders, HttpStatus.OK);
    }
  
    /** 문자인증 */
    @PostMapping ("/sms")
    public Object smsAuth(@RequestBody SendSmsRequest sendSmsRequest) throws JsonProcessingException, ParseException, UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException {
        String phone = sendSmsRequest.getPhone().replaceAll("-","");
        String randomCode = sendSmsRequest.getRandomCode();

        smsService.sendSms(phone,randomCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}