package com.a506.blockai.api.controller;

import com.a506.blockai.api.dto.request.SendSmsRequest;
import com.a506.blockai.api.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/ai/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    final private SmsService smsService;

    @PostMapping ("/sms")
    public Object smsAuth(@RequestBody SendSmsRequest sendSmsRequest) throws JsonProcessingException, ParseException, UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException {
        String phone = sendSmsRequest.getPhone().replaceAll("-","");
        String randomCode = sendSmsRequest.getRandomCode();

        smsService.sendSms(phone,randomCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}