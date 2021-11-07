package com.a506.blockai.Service;

public interface AiService {
    String createProfile();
    String enrollment(String profileId);
    float identify(String profileId);
}
