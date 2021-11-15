package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.FaceBiometricsRequest;
import com.a506.blockai.api.dto.request.VoiceBiometricsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface AiService {

    String createProfile();
    String enrollment(String profileId, VoiceBiometricsRequest voiceBiometricsRequest);
    float identifyVoice(String profileId, VoiceBiometricsRequest voiceBiometricsRequest);
    float identifyFace(FaceBiometricsRequest faceBiometricsRequest) throws Exception;

    String saveFace(String encodedUserFace, String userId) throws IOException;
}
