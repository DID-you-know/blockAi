package com.a506.blockai.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FaceBiometricResponse {

    private String encodeFaceFile;

    public static FaceBiometricResponse from(String encodeFaceFile) {
        return new FaceBiometricResponse(encodeFaceFile);
    }
}
