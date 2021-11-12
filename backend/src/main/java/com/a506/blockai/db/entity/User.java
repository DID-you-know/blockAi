package com.a506.blockai.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Date birth;

    @NotNull
    private String phone;

    @Embedded
    private DID did;

    @OneToMany(mappedBy = "user")
    private List<Certification> certifications = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public User(String name, String email, String password, Date birth, String phone, DID did) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.phone = phone;
        this.did = did;
    }

    public void addCertification(Certification certification) {
        this.certifications.add(certification);
        certification.to(this);
    }
}