package com.a506.blockai.api.service;

public interface AiService {
    String createProfile();
    String enrollment(String profileId);
    float identify(String profileId);
}
