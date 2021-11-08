package com.a506.blockai.api.controller;

import com.a506.blockai.dto.request.BiometricsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certification")
public class CertificationController {

    @PostMapping("/users/{userId}")
    public ResponseEntity<Void> certifyBiometrics(@PathVariable int userId, @RequestBody BiometricsRequest biometricsRequest) {
        
        return ResponseEntity.noContent().build();
    }
}