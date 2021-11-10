package com.a506.blockai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Configuration
public class AzureConfig {
    @Value("${cloud.azure.access-key}")
    private String accesskey;

    @Bean
    public String accessKey() {return accesskey; }
}
