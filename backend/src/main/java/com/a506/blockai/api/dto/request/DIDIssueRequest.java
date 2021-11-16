package com.a506.blockai.api.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DIDIssueRequest {
    private String facePath;
    private String voiceId;
}
