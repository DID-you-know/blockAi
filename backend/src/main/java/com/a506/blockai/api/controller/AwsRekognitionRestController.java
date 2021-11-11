package com.a506.blockai.api.controller;

import com.a506.blockai.api.service.DetectFace;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/face")
@RestController
public class AwsRekognitionRestController {

//    private AwsRekognitionService awsRekognitionService;
//
//    public AwsRekognitionRestController(AwsRekognitionService awsRekognitionService) {
//        this.awsRekognitionService = awsRekognitionService;
//    }
//
//    @PostMapping("/images/moderation-labels")
//    public Object detectModerationLabels(@RequestParam MultipartFile image) throws IOException {
//        return ResponseEntity.ok(awsRekognitionService.detectModerationLabels(image));
//    }

    @Autowired
    private DetectFace detectFace;

    @PostMapping("/rekognition")
    public void detect(@RequestBody @ApiParam(value="encoding된 이미지 문자열") String base64) throws Exception {
        detectFace .detect(base64);
    }

}