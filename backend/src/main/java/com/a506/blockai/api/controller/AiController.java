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

import java.io.IOException;

@RestController
@RequestMapping("/api/ai/")
@RequiredArgsConstructor
public class AiController {

    final private AiService aiService;

    @PostMapping("/{voiceId}/voice")
    public ResponseEntity<?> identify(@PathVariable String voiceId, @RequestBody VoiceBiometricsRequest voiceBiometricsRequest) throws IOException {
        return ResponseEntity.status(200).body(aiService.identify(voiceId,voiceBiometricsRequest));
    }

    @PostMapping("/{userId}/face")
    public ResponseEntity<?>detect(@PathVariable String userId, @RequestBody FaceBiometricsRequest faceBiometricsRequest) throws Exception {
       float result = aiService.detectFace(faceBiometricsRequest.getFace());

        System.out.println(aiService.saveFace(faceBiometricsRequest.getFace(), userId));

        return ResponseEntity.status(200).body(result);
    }
}
