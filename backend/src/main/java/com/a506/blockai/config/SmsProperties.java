package com.a506.blockai.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("sens")
public final class SmsProperties {

    private final String accessKey;
    private final String serviceKey;
    private final String secretKey;
    private final String fromNum;
}
