package com.a506.blockai;

import com.a506.blockai.api.service.EthereumService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;


//@SpringBootApplication
@SpringBootApplication(exclude = ContextRegionProviderAutoConfiguration.class)
public class BlockaiApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException {

		SpringApplication.run(BlockaiApplication.class, args);

	}

}
