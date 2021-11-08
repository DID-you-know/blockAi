package com.a506.blockai.controller;

import com.a506.blockai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/")
@RequiredArgsConstructor
public class AiController {

    final private AiService aiService;

    @PostMapping ("/voice/profile")
    public ResponseEntity<?> createProfile() {
        String profileId = aiService.createProfile();

        //제대로 등록됐을 때(null값 아님)
        if(profileId!=null) return ResponseEntity.status(200).body(profileId);
        else return ResponseEntity.status(400).body(profileId); //오류코드있을 때
    }

    @PostMapping("/voice/enrollment/{profileId}")
    public ResponseEntity<?> enrollment(@PathVariable String profileId) {
        String enrollmentStatus = aiService.enrollment(profileId);

        //profileId에 음성등록 완료시
        if(enrollmentStatus.equals("201")) return ResponseEntity.status(200).body(enrollmentStatus);
        else  return ResponseEntity.status(400).body(enrollmentStatus); //오류코드있을 때
    }

    @GetMapping("/voice/identify/{profileId}")
    public ResponseEntity<?> identify(@PathVariable String profileId) {
        return ResponseEntity.status(200).body(aiService.identify(profileId));
    }
}
