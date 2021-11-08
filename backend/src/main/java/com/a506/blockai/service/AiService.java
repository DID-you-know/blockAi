package com.a506.blockai.service;

public interface AiService {
    String createProfile();
    String enrollment(String profileId);
    float identify(String profileId);
}
