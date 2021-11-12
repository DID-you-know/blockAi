//package com.a506.blockai.config;
//
//import com.amazonaws.auth.AWSCredentialsProvider;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.aws.core.io.s3.SimpleStorageProtocolResolver;
//import org.springframework.cloud.aws.core.region.RegionProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.DefaultResourceLoader;
//
//@Configuration
//public class S3Config {
//
//    @Bean("amazonS3Client") // THIS METHOD IS NOT BEING CALLED!!!
//    public AmazonS3 amazonS3Client(AWSCredentialsProvider credentialsProvider,
//                                   RegionProvider regionProvider,
//                                   @Value("${cloud.aws.s3.default-endpoint}") String endpoint) {
//        return AmazonS3ClientBuilder.standard()
//                .withCredentials(credentialsProvider)
//                .withEndpointConfiguration(
//                        new AwsClientBuilder.EndpointConfiguration(endpoint, regionProvider.getRegion().getName()))
//                .build();
//    }
//
//    @Autowired  // THIS METHOD IS NOT BEING CALLED!!!
//    public void configureResourceLoader(@Qualifier("amazonS3Client") AmazonS3 amazonS3Client,
//                                        DefaultResourceLoader resourceLoader) {
//        SimpleStorageProtocolResolver simpleStorageProtocolResolver = new SimpleStorageProtocolResolver(amazonS3Client);
//        // As we are calling it by hand, we must initialize it properly.
//        simpleStorageProtocolResolver.afterPropertiesSet();
//        resourceLoader.addProtocolResolver(simpleStorageProtocolResolver);
//    }
//}