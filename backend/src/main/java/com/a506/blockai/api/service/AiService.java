package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.VoiceBiometricsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface AiService {
    String createProfile();
    String enrollment(String profileId, VoiceBiometricsRequest voiceBiometricsRequest);
    float identify(String profileId, VoiceBiometricsRequest voiceBiometricsRequest);
    float identifyFace(String encodedString, String savedUserUrl) throws Exception;
    String saveFace(String encodedUserFace, String userId) throws IOException;
}
