package com.a506.blockai.db.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id")
    private int id;
    private String certifiedBy;
    @CreatedDate
    private LocalDateTime certifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Certification(String certifiedBy) {
        this.certifiedBy = certifiedBy;
        this.certifiedAt = LocalDateTime.now();
    }

    public void to(User user) {
        this.user = user;
    }
}