package com.a506.blockai.db.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DID {

    @Column(unique = true)
    private String didAddress;
    private LocalDateTime issuedAt;
    private LocalDateTime updatedAt;

    public DID(String didAddress) {
        this.didAddress = didAddress;
        this.issuedAt = this.updatedAt = LocalDateTime.now();
    }

    public void updateDid(String didAddress) {
        this.didAddress = didAddress;
        this.updatedAt = LocalDateTime.now();
    }
}
