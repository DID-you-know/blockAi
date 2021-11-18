package com.a506.blockai.api.controller;

import com.a506.blockai.api.dto.request.LoginRequest;
import com.a506.blockai.api.dto.request.SignupRequest;
import com.a506.blockai.api.dto.response.LogResponse;
import com.a506.blockai.api.dto.response.LoginResponse;
import com.a506.blockai.api.dto.response.UserIdResponse;
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
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

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
    private final SmsService smsService;

    /** 회원가입 */
    @PostMapping("")
    @ApiOperation(value = "회원가입", notes = "<strong>이메일, 패스워드, 이름, 생일, 전화번호</strong>을 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 성공"),
    })
    public ResponseEntity signup (@RequestBody SignupRequest signupRequest) throws ParseException {
        userService.register(signupRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    /** 로그인 */
    @PostMapping("/login")
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
    @ApiOperation(value = "문자인증", notes = "휴대폰번호와 랜덤번호를 받아 인증을 진행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "문자인증 성공"),
    })
    public Object smsAuth(@RequestBody SendSmsRequest sendSmsRequest) throws JsonProcessingException, ParseException, UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException {
        String phone = sendSmsRequest.getPhone().replaceAll("-","");
        String randomCode = sendSmsRequest.getRandomCode();

        smsService.sendSms(phone,randomCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "폰번호로 유저정보 조회", notes = "<strong>핸드폰 번호</strong>를 통해 userId를 반환한다.")
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<UserIdResponse> getUserIdByPhoneNumber(@PathVariable String phoneNumber) {
        int id = userService.getUserIdByPhoneNumber(phoneNumber);
        UserIdResponse res = new UserIdResponse(id);
        return ResponseEntity.status(200).body(res);
    }
    @GetMapping("/{userId}/log")
    @ApiOperation(value = "인증로그", notes = "입력한 userId의 인증기록을 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "인증로그 불러오기 성공"),
    })
    public ResponseEntity<LogResponse> getCertLog (@PathVariable int userId) {
        List<LogResponse> list = userService.certLog(userId);
        return new ResponseEntity(list, HttpStatus.OK);
    }
}