package com.a506.blockai.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class FaceBiometricRequest {

    private String encodedUserFace;
    private String savedS3UserFaceUrl;

    public FaceBiometricRequest(String encodedUserFace, String savedS3UserFaceUrl) {
        this.encodedUserFace = encodedUserFace;
        this.savedS3UserFaceUrl = savedS3UserFaceUrl;
    }
}
