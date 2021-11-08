package com.a506.blockai.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.sql.Time;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@DynamicInsert
@AllArgsConstructor
@RequiredArgsConstructor
public class DID {

    @Id
    private int id;

    @NotNull
    @OneToOne(fetch = LAZY)          // 일대일 관계에서 FK를 가지고 있는 애가 주인. 즉 얘가 주인
    @JoinColumn(name = "user_id")     // 연관관계의 주인은 mappedBy X, JoinColumn 사용
    private User user;

    @NotNull
    private String from;

    @NotNull
    private Time certifiedDate;
}
