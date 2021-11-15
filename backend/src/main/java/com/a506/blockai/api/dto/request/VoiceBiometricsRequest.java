package com.a506.blockai.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class VoiceBiometricsRequest {

    private String encodedUserVoice;
    private String savedS3UserVoiceUrl;

    public VoiceBiometricsRequest(String encodedUserVoice, String savedS3UserVoiceUrl) {
        this.encodedUserVoice = encodedUserVoice;
        this.savedS3UserVoiceUrl = savedS3UserVoiceUrl;
    }
}
