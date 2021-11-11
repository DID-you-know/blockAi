package com.a506.blockai.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class FaceBiometricsRequest {

    private String face;

    public FaceBiometricsRequest(String face) {
        this.face = face;
    }
}
