package com.a506.blockai;

import com.a506.blockai.api.service.EthereumService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.text.ParseException;

import java.util.concurrent.ExecutionException;


//@SpringBootApplication
@SpringBootApplication(exclude = ContextRegionProviderAutoConfiguration.class)
// @ConfigurationPropertiesScan
public class BlockaiApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException, JsonProcessingException, ParseException, UnsupportedEncodingException, URISyntaxException, InvalidKeyException {

		SpringApplication.run(BlockaiApplication.class, args);

	}

}
