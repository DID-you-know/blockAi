package com.a506.blockai.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("LoginTokenResponse")
public class LoginResponse {

    @ApiModelProperty(name = "JWT 인증 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzNTRAbmF2ZXI...")
    private String token;

    private int id;

    private String name;

    private LocalDateTime issuedAt;
}