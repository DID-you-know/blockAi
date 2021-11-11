package com.a506.blockai.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value={AzureProperties.class})
public class AzureConfiguration {
}


