package com.a506.blockai.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoiceBiometricResponse {

    private String encodeVoiceFile;

    public static VoiceBiometricResponse from(String encodeVoiceFile) {
        return new VoiceBiometricResponse(encodeVoiceFile);
    }
}
