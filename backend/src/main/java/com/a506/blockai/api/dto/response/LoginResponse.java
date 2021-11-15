package com.a506.blockai.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("LoginTokenResponse")
public class LoginResponse {

    @ApiModelProperty(name = "JWT 인증 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzNTRAbmF2ZXI...")
    private String token;

    @ApiModelProperty(name = "사용자 아이디", example = "test@naver.com")
    private int id;

    @ApiModelProperty(name = "사용자 이름", example = "김블록")
    private String name;

    @ApiModelProperty(name = "DID 발급 현황", example = "DID가 있으면 '2021-11-15 11:25:01', 없으면 null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issuedAt;
}