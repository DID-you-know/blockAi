package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.VoiceBiometricsRequest;

public interface AiService {
    String createProfile();
    String enrollment(String profileId, VoiceBiometricsRequest voiceBiometricsRequest);
    float identify(String profileId, VoiceBiometricsRequest voiceBiometricsRequest);
}
