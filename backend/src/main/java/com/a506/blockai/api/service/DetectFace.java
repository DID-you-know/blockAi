package com.a506.blockai.api.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;

import java.io.FileOutputStream;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.java_websocket.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.filechooser.FileSystemView;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DetectFace {

    @Autowired
    private AmazonRekognition rekognitionClient;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void detect(String encodedString) throws Exception {

        String url = amazonS3Client.getUrl(bucket, "test.png").toString();
        System.out.println(url);

        Float similarityThreshold = 70F;

        byte[] decodedBytes = Base64.decode(encodedString);
        String decodedString = new String(decodedBytes);

        System.out.println(FileSystemView.getFileSystemView().getHomeDirectory());
        File file = new File(FileSystemView.getFileSystemView().getHomeDirectory()
                + "img.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(decodedBytes);
        fileOutputStream.close();

        String sourceImage = decodedString;
        String targetImage = decodedString;

        ByteBuffer sourceImageBytes=null;
        ByteBuffer targetImageBytes=null;

        //Load source and target images and create input parameters
        try (InputStream inputStream = new FileInputStream(file)) {
            sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load source image " + sourceImage);
            System.exit(1);
        }
        try (InputStream inputStream = new FileInputStream(file)) {
            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load target images: " + targetImage);
            System.exit(1);
        }

        Image source=new Image()
                .withBytes(sourceImageBytes);
        Image target=new Image()
                .withBytes(targetImageBytes);

        DetectLabelsRequest detect_request = new DetectLabelsRequest()
                .withImage(new Image()
                .withS3Object(new S3Object()
                        .withName("test.png").withBucket(bucket)))
                .withMaxLabels(10)
                .withMinConfidence(75F);

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(source)
                .withTargetImage(target)
                .withSimilarityThreshold(similarityThreshold);

        // Call operation
        CompareFacesResult compareFacesResult=rekognitionClient.compareFaces(request);


        // Display results
        List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
        for (CompareFacesMatch match: faceDetails){
            ComparedFace face= match.getFace();
            BoundingBox position = face.getBoundingBox();
            System.out.println("Face at " + position.getLeft().toString()
                    + " " + position.getTop()
                    + " matches with " + match.getSimilarity().toString()
                    + "% confidence.");

        }
        List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();

        System.out.println("There was " + uncompared.size()
                + " face(s) that did not match");
    }

}