package com.a506.blockai.api.dto.response;

import com.a506.blockai.db.entity.Certification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertificationResponse {

    private int id;
    private String certifiedBy;
    private LocalDateTime certifiedAt;

    public static CertificationResponse from(Certification certification) {
        return new CertificationResponse(certification.getId(), certification.getCertifiedBy(), certification.getCertifiedAt());
    }
}
