package com.a506.blockai.api.controller;

import com.a506.blockai.api.dto.request.FaceBiometricsRequest;
import com.a506.blockai.api.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/")
@RequiredArgsConstructor
public class AiController {

    final private AiService aiService;

//    @PostMapping("/{voiceId}/voice")
//    public ResponseEntity<?> identify(@PathVariable String voiceId, @RequestBody VoiceBiometricsRequest voiceBiometricsRequest) throws IOException {
//        return ResponseEntity.status(200).body(aiService.identify(voiceId,voiceBiometricsRequest));
//    }

    @PostMapping("/{userId}/face")
    public ResponseEntity<?> identifyFace(@PathVariable String userId, @RequestBody FaceBiometricsRequest faceBiometricsRequest) throws Exception {
        String savedUserUrl="";
       float result = aiService.identifyFace(faceBiometricsRequest);

        return ResponseEntity.status(200).body(result);
    }
}
