package com.a506.blockai.api.controller;

import com.a506.blockai.api.dto.request.BiometricsCertificateRequest;
import com.a506.blockai.api.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certification")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<Void> certifyBiometrics(@PathVariable int userId, @RequestBody BiometricsCertificateRequest biometricsCertificateRequest) throws Exception {
        certificationService.certifyBiometrics(userId, biometricsCertificateRequest);
        return ResponseEntity.ok().build();
    }
}