package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.VoiceBiometricsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface AiService {
    float identify(String profileId, VoiceBiometricsRequest voiceBiometricsRequest) throws IOException;
    float detectFace(String encodedString) throws Exception;
    String saveFace(String encodedUserFace, String userId) throws IOException;
}
