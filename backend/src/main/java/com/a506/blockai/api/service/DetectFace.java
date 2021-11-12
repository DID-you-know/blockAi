//package com.a506.blockai.api.service;
//
//import com.a506.blockai.config.AwsProperties;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.rekognition.AmazonRekognition;
//import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
//import com.amazonaws.services.rekognition.model.*;
//
//import java.io.FileOutputStream;
//import java.util.List;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.nio.ByteBuffer;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.GetObjectRequest;
//import com.amazonaws.util.IOUtils;
//import lombok.RequiredArgsConstructor;
//import org.java_websocket.util.Base64;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;
//
//import javax.swing.filechooser.FileSystemView;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class DetectFace {
//
//    @Bean
//    public AmazonRekognition amazonRekognition(AwsProperties awsProperties) {
//        System.out.println(awsProperties.getAccessKey());
//        BasicAWSCredentials credentials = new BasicAWSCredentials(awsProperties.getAccessKey(), awsProperties.getSecretKey());
//        return AmazonRekognitionClientBuilder
//                .standard()
//                .withRegion(Regions.AP_NORTHEAST_2)
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .build();
//    }
//
//    @Autowired
//    private AmazonRekognition rekognitionClient;
//
//    private final AmazonS3 amazonS3Client;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    public float detect(String encodedString) throws Exception {
//
//        String url = amazonS3Client.getUrl(bucket, "test.png").toString();
//        System.out.println(url);
//
//        Float similarityThreshold = 70F;
//
//        // 프론트에서 넘어온 [촬영된 현재 사용자 이미지]
//        byte[] decodedBytes = Base64.decode(encodedString);
//        String decodedString = new String(decodedBytes);
//
//        File inputImage = new File(FileSystemView.getFileSystemView().getHomeDirectory()
//                + "img.jpg");
//        FileOutputStream fileOutputStream = new FileOutputStream(inputImage);
//        fileOutputStream.write(decodedBytes);
//        fileOutputStream.close();
//
//        // DID가 복호화한 [기존 저장된 사용자 이미지]
//        com.amazonaws.services.s3.model.S3Object originImage = amazonS3Client.getObject(new GetObjectRequest(bucket, "s3://blockai-bucket/test.png"));
//
//
//        String sourceImage = decodedString;
//        String targetImage = decodedString;
//
//        ByteBuffer sourceImageBytes=null;
//        ByteBuffer targetImageBytes=null;
//
//        //Load source and target images and create input parameters
//        try (InputStream inputStream = new FileInputStream(inputImage)) {
//            sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
//        }
//        catch(Exception e)
//        {
//            System.out.println("Failed to load source image " + sourceImage);
//            System.exit(1);
//        }
////        try (InputStream inputStream = new FileInputStream(inputImage)) {
////            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
////        }
//        try (InputStream inputStream = originImage.getObjectContent()) {
//            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
//        }
//        catch(Exception e)
//        {
//            System.out.println("Failed to load target images: " + targetImage);
//            System.exit(1);
//        }
//
//        Image source=new Image()
//                .withBytes(sourceImageBytes);
//        Image target=new Image()
//                .withBytes(targetImageBytes);
//
//        DetectLabelsRequest detect_request = new DetectLabelsRequest()
//                .withImage(new Image()
//                        .withS3Object(new S3Object()
//                                .withName("test.png").withBucket(bucket)))
//                .withMaxLabels(10)
//                .withMinConfidence(75F);
//
//        CompareFacesRequest request = new CompareFacesRequest()
//                .withSourceImage(source)
//                .withTargetImage(target)
//                .withSimilarityThreshold(similarityThreshold);
//
//        // Call operation
//        CompareFacesResult compareFacesResult=rekognitionClient.compareFaces(request);
//
//        float result=0;
//
//        // Display results
//        List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
//        for (CompareFacesMatch match: faceDetails){
//            ComparedFace face= match.getFace();
//            BoundingBox position = face.getBoundingBox();
//            System.out.println("Face at " + position.getLeft().toString()
//                    + " " + position.getTop()
//                    + " matches with " + match.getSimilarity().toString()
//                    + "% confidence.");
//            result=match.getSimilarity();
//        }
//        List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();
//
//        System.out.println("There was " + uncompared.size()
//                + " face(s) that did not match");
//
//        return result;
//    }
//
//}