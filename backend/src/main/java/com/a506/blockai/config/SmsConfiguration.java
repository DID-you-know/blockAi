package com.a506.blockai.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value={SmsProperties.class})
public class SmsConfiguration {
}
