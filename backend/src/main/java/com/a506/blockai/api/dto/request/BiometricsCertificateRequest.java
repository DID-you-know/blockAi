package com.a506.blockai.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BiometricsCertificateRequest {

    private String face;
    private String voice;

    public BiometricsCertificateRequest(String face, String voice) {
        this.face = face;
        this.voice = voice;
    }
}
