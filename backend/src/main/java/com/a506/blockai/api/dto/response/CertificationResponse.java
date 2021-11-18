package com.a506.blockai.api.dto.response;

import com.a506.blockai.db.entity.Certification;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime certifiedAt;

    public static CertificationResponse from(Certification certification) {
        return new CertificationResponse(certification.getId(), certification.getCertifiedBy(), certification.getCertifiedAt());
    }
}
