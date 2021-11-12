package com.a506.blockai.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

@Getter
@Setter
public class SignupRequest {

    @ApiModelProperty(name = "유저 email", example = "test@naver.com")
    @NotNull(message = "email may not be empty")
    @Email(message = "이메일 형식이 아닙니다.")
    @Size(min = 3, max = 50)
    private String email;

    @ApiModelProperty(name = "유저 password", example = "root1234!")
    @NotNull(message = "password may not be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
            message = "비밀번호는 영문, 숫자, 특수문자가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @ApiModelProperty(name="유저 name", example = "김블록")
    @NotNull(message = "nickname may not be empty")
    @Size(min = 1, max = 30)
    private String name;

    @ApiModelProperty(name = "유저 birth", example = "1999.02.22")
    @NotNull(message = "birth may not be empty")
    @Size(min = 10, max = 10)
    private String birth;

    @ApiModelProperty(name = "유저 phone", example = "010-2345-6868")
    @NotNull(message = "phone number may not be empty")
    @Size(min = 11, max = 11)
    private String phone;

}