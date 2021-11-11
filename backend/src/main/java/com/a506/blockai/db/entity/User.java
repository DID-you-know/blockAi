package com.a506.blockai.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@DynamicInsert
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // PK 생성규칙 설정. AutoIncrement 설정
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Date birth;

    private String phone;
    private int role;

    @NotNull
    private LocalDateTime created_at;

    @NotNull

    private String public_key;

    @NotNull
    private String private_key;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Certification> certification = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private DID did;

}