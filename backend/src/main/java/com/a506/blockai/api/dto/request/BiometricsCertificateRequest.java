package com.a506.blockai.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BiometricsCertificateRequest {

    private String face;
    private String voice;
    private String certifiedBy;

    public BiometricsCertificateRequest(String face, String voice, String certifiedBy) {
        this.face = face;
        this.voice = voice;
        this.certifiedBy = certifiedBy;
    }
}
