package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.VoiceBiometricsRequest;
import com.a506.blockai.config.AwsProperties;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.wave.Wave;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.java_websocket.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;


@Getter
@Setter
@Service("aiService")
@RequiredArgsConstructor
public class AiServiceImpl implements AiService{

    @Bean
    public AmazonRekognition amazonRekognition(AwsProperties awsProperties) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(awsProperties.getAccessKey(), awsProperties.getSecretKey());

        return AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(awsProperties.getAccessKey(), awsProperties.getSecretKey());
        return AmazonS3Client.builder()
                .standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    @Autowired
    private AmazonRekognition rekognitionClient;
    @Autowired
    AwsProperties awsProperties;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /* voice detection */
    @Override
    public float identify(String voiceId, VoiceBiometricsRequest voiceBiometricsRequest) throws IOException {

        System.out.println("들어옴");

        // DID가 복호화한 [기존 저장된 사용자 이미지]
//      com.amazonaws.services.s3.model.S3Object originImage = amazonS3Client.getObject(new GetObjectRequest(bucket, "s3://blockai-bucket/test.png"));

        byte[] decoded = Base64.decode(voiceBiometricsRequest.getVoice());
        File recordFile = null;

        try
        {
            recordFile = new File("record.wav");
            FileOutputStream os = new FileOutputStream(recordFile, true);
            os.write(decoded);
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String file1 = recordFile.getName();
        String file2 = recordFile.getName();

        Wave w1 = new Wave(file1);
        Wave w2 = new Wave(file2);

        FingerprintSimilarity fps = w1.getFingerprintSimilarity(w2);
        float score = fps.getScore();
        float sim = fps.getSimilarity();
        System.out.println(score+" "+sim);

        return score;
    }


    /* face detection */

    public File decodeImage(String encodedString) throws IOException {
        byte[] decodedBytes = Base64.decode(encodedString);
        String decodedString = new String(decodedBytes);

        // 들어온 이미지를 file 형태로 변경
        File inputImage = new File(FileSystemView.getFileSystemView().getHomeDirectory()
                + "img.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream(inputImage);
        fileOutputStream.write(decodedBytes);
        fileOutputStream.close();

        return inputImage;
    }

    @Override
    public float detectFace(String encodedUserFace) throws Exception {

        Float similarityThreshold = 70F;

        // 프론트에서 넘어온 [촬영된 현재 사용자 이미지]를 file 형태로 변경
        File inputImage = decodeImage(encodedUserFace);

        // S3에서 이미지 받아오는데 쓰이는 amazonS3Client
        AmazonS3 amazonS3Client = amazonS3Client();

        // DID가 복호화한 [기존 저장된 사용자 이미지]
        // 여기 key 부분을 수정해야함. 사용자 이미지 이름으로!
        // s3://blockai-bucket/test.png 이런식으로 넘어오면 -> 앞 경로 컷하고 뒤의 이름만 가져와도 될듯.
        String key = "test.png";
        com.amazonaws.services.s3.model.S3Object originImage = amazonS3Client.getObject(new GetObjectRequest(bucket, key));

        ByteBuffer sourceImageBytes = null;
        ByteBuffer targetImageBytes = null;

        //Load source and target images and create input parameters
        try (InputStream inputStream = new FileInputStream(inputImage)) {
            sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        } catch (Exception e) {
            System.out.println("Failed to load source image");
            System.exit(1);
        }
        try (InputStream inputStream = originImage.getObjectContent()) {
            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        } catch (Exception e) {
            System.out.println("Failed to load target images");
            System.exit(1);
        }

        Image source = new Image()
                .withBytes(sourceImageBytes);
        Image target = new Image()
                .withBytes(targetImageBytes);

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(source)
                .withTargetImage(target)
                .withSimilarityThreshold(similarityThreshold);

        // Call operation
        CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request);

        float result = 0;

        // Display results
        List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
        for (CompareFacesMatch match : faceDetails) {
            ComparedFace face = match.getFace();
            BoundingBox position = face.getBoundingBox();
//            System.out.println("Face at " + position.getLeft().toString()
//                    + " " + position.getTop()
//                    + " matches with " + match.getSimilarity().toString()
//                    + "% confidence.");
            result = match.getSimilarity();
        }
        // List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();

        return result;
    }

    @Override
    public String saveFace(String encodedUserFace, String userId) throws IOException {

        // 프론트에서 넘어온 등록해야할 [촬영된 현재 사용자 이미지]
        File inputImage = decodeImage(encodedUserFace);

        AmazonS3 amazonS3Client = amazonS3Client();
        String fileName = userId; // 이렇게 저장해도 되겠지?
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputImage).withCannedAcl(CannedAccessControlList.PublicRead));
        String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();

        return uploadImageUrl;
    }

}
