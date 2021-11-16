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
@ApiModel("LogResponse")
public class LogResponse {

    @ApiModelProperty(name = "인증 아이디", example = "1")
    private int id;

    @ApiModelProperty(name = "인증 발급처", example = "발급처 어디 편의점")
    private String certified_by;

    @ApiModelProperty(name = "인증시간", example = "2020-11-22 11:23:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime certified_at;
}
