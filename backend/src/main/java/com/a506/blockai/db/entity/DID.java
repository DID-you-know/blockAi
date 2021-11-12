package com.a506.blockai.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@DynamicInsert
@AllArgsConstructor
@RequiredArgsConstructor
public class DID {
    @Id
    private int id;

    @NotNull
    @OneToOne(fetch = LAZY)          // 일대일 관계에서 FK를 가지고 있는 애가 주인. 즉 얘가 주인
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
    private User user;

    private String didAddress;

    private boolean didFlag;

    @NotNull
    private LocalDateTime issuedDate;

}
