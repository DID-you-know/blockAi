package com.a506.blockai.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class VoiceBiometricsRequest {

    private String voice;

    public VoiceBiometricsRequest(String voice) {
        this.voice = voice;
    }
}
