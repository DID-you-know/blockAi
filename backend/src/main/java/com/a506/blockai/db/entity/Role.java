package com.a506.blockai.db.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    @Getter
    private final String type;

    @Override
    public String getAuthority() {
        return type;
    }
}