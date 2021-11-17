package com.a506.blockai.api.dto.request;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessagesRequest {
    private String to;
    private String content;
}
