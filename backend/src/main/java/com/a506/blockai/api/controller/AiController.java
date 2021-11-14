package com.a506.blockai.api.controller;

import com.a506.blockai.api.dto.request.FaceBiometricsRequest;
import com.a506.blockai.api.dto.request.VoiceBiometricsRequest;
import com.a506.blockai.api.service.AiService;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/")
@RequiredArgsConstructor
public class AiController {

    final private AiService aiService;

    @PostMapping ("/users/{userId}/voice/issue")
    public ResponseEntity<?> createProfile(@PathVariable int userId, @RequestBody VoiceBiometricsRequest voiceBiometricsRequest) {
        String profileId = aiService.createProfile();
        String enrollmentStatus = aiService.enrollment(profileId, voiceBiometricsRequest);

        //profileId에 음성등록 완료시
        if(enrollmentStatus.equals("200")) return ResponseEntity.status(200).body(profileId);
        else  return ResponseEntity.status(400).body("ErrorCode" + enrollmentStatus); //오류코드있을 때
    }

    @PostMapping("/{voiceId}/voice")
    public ResponseEntity<?> identify(@PathVariable String voiceId, @RequestBody VoiceBiometricsRequest voiceBiometricsRequest) {
        return ResponseEntity.status(200).body(aiService.identify(voiceId,voiceBiometricsRequest));
    }

    @PostMapping("/{userId}/face")
    public ResponseEntity<?>detect(@PathVariable String userId, @RequestBody FaceBiometricsRequest faceBiometricsRequest) throws Exception {
       float result = aiService.detectFace(faceBiometricsRequest.getFace());

       aiService.saveFace(faceBiometricsRequest.getFace(), userId);

        return ResponseEntity.status(200).body(result);
    }
}
