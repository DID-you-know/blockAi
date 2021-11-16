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
@ApiModel("get userID by Phone Number")
public class UserIdResponse {
    @ApiModelProperty(name = "사용자 아이디", example = "1")
    private int userId;
}