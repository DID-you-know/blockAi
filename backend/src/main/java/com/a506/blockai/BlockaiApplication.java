package com.a506.blockai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;

@SpringBootApplication(exclude = ContextRegionProviderAutoConfiguration.class)
public class BlockaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockaiApplication.class, args);
	}
}
