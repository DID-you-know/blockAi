package com.a506.blockai.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

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

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Certification> certification = new ArrayList<>();

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

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}