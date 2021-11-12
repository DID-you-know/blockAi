package com.a506.blockai.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("ethereum")
public final class EthereumProperties {

    private final String from;
    private final String contract;
    private final String networkUrl;
    private final String privateKey;
    private final String RSAPrivateKey;
    private final String RSAPublicKey;

}
