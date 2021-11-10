package com.a506.blockai.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DIDIssueRequest {

    private String facePath;
    private String voiceId;

    public DIDIssueRequest(String facePath, String voiceId) {
        this.facePath = facePath;
        this.voiceId = voiceId;
    }
}
