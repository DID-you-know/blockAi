package com.a506.blockai.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("azure")
public final class AzureProperties {

    private final String accesskey;
}


