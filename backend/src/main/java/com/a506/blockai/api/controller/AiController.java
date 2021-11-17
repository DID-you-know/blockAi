package com.a506.blockai.api.controller;

import com.a506.blockai.api.dto.request.FaceBiometricRequest;
import com.a506.blockai.api.dto.request.VoiceBiometricRequest;
import com.a506.blockai.api.dto.response.FaceBiometricResponse;
import com.a506.blockai.api.dto.response.VoiceBiometricResponse;
import com.a506.blockai.api.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

@RestController
@RequestMapping("/api/ai/")
@RequiredArgsConstructor
public class AiController {

    final private AiService aiService;

    @PostMapping("/{voiceId}/voice")
    public ResponseEntity<?> identify(@PathVariable String voiceId, @RequestBody VoiceBiometricRequest voiceBiometricsRequest) throws IOException, UnsupportedAudioFileException {
        return ResponseEntity.status(200).body(aiService.identifyVoice(voiceBiometricsRequest));
    }

    @PostMapping("/{userId}/face")
    public ResponseEntity<?> identifyFace(@PathVariable String userId, @RequestBody FaceBiometricRequest faceBiometricsRequest) throws Exception {
        float result = aiService.identifyFace(faceBiometricsRequest);
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/users/{userId}/face")
    public ResponseEntity<FaceBiometricResponse> searchFace(@PathVariable int userId) throws Exception {
        return ResponseEntity.ok(aiService.getFaceData(userId));
    }

    @GetMapping("/users/{userId}/voice")
    public ResponseEntity<VoiceBiometricResponse> searchVoice(@PathVariable int userId) throws Exception {
        return ResponseEntity.ok(aiService.getVoiceData(userId));
    }

}
