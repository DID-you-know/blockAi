package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.FaceBiometricsRequest;
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
import com.amazonaws.services.s3.model.S3ObjectInputStream;
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
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Getter
@Setter
@Service("aiService")
@RequiredArgsConstructor
public class AiService {

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

    private final AmazonS3Client amazonS3Client;
    @Autowired
    AwsProperties awsProperties;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    /* voice detection */
    public float identifyVoice(VoiceBiometricsRequest voiceBiometricsRequest) throws IOException {

        String encodedUserVoice = voiceBiometricsRequest.getEncodedUserVoice(); //프론트에서 base64로 변환되어 넘어온 녹음된 파일
        String savedS3UserVoiceUrl = voiceBiometricsRequest.getSavedS3UserVoiceUrl(); //DID발급 시 블록체인에 등록한 음성파일(S3 저장위치)

        AmazonS3 amazonS3Client = amazonS3Client();
        //저장된 S3에 들어있는 음성파일 불러오기
        String fileName = savedS3UserVoiceUrl.split("/")[3];
        com.amazonaws.services.s3.model.S3Object savedUserVoice = amazonS3Client.getObject(new GetObjectRequest(bucket, fileName));

        //저장경로 지정
        String rootPath = System.getProperty("user.dir");;
        String movePath = "/src/main/java/com/a506/blockai/common/tmp/";
        System.out.println("현재 프로젝트의 경로 : "+rootPath );

        //1. base64로 변환된 음섣파일 decode해서 다시 wav파일로 변환
        byte[] decoded = Base64.decode(encodedUserVoice);
        InputStream record_in = new ByteArrayInputStream(decoded);
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(
                rootPath+movePath+"record.wav"  ));
        dos.write(decoded);
        File recordFile = new File(rootPath+movePath+"record.wav");


        //2. S3저장되어 있던 음성파일 File로 변환
        S3ObjectInputStream s3is = savedUserVoice.getObjectContent();
        FileOutputStream fos = new FileOutputStream(new File(rootPath+movePath+"saved.wav" ));
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = s3is.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        s3is.close();
        fos.close();
        File savedFile = new File(rootPath+movePath+"saved.wav");


        Wave w1 = new Wave(recordFile.getPath());
        Wave w2 = new Wave(savedFile.getPath());
       //Wave w2 = new Wave(savedFile.getPath());

        FingerprintSimilarity fps = w1.getFingerprintSimilarity(w2);
        float fileScore = fps.getScore(); //유사위치 수
        float score = fps.getSimilarity(); //유사도

        //파일 삭제
        recordFile.delete();
        savedFile.delete();
        return score;
    }

    /* face detection */

    public File decodeImage(String encodedString) throws IOException {
        byte[] decodedBytes = Base64.decode(encodedString);
        String decodedString = new String(decodedBytes);

        // 들어온 이미지를 file 형태로 변경
//        File inputImage = new File(FileSystemView.getFileSystemView().getHomeDirectory()
//                + "img.jpg");

        String rootPath = System.getProperty("user.dir");;
        String movePath = "/src/main/java/com/a506/blockai/common/tmp/";

        File inputImage = new File(rootPath+movePath + "img.jpg");

        FileOutputStream fileOutputStream = new FileOutputStream(inputImage);
        fileOutputStream.write(decodedBytes);
        fileOutputStream.close();

        return inputImage;
    }

    public File getFile(String filePath) throws IOException {

        // S3에서 이미지 받아오는데 쓰이는 amazonS3Client
        AmazonS3 amazonS3Client = amazonS3Client();

        InputStream in = amazonS3Client.getObject(bucket, filePath).getObjectContent();
        File file = File.createTempFile("s3file", "");
        Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file;
    }


    public float identifyFace(FaceBiometricsRequest faceBiometricsRequest) throws Exception {

        Float similarityThreshold = 70F;

        // 프론트에서 넘어온 [촬영된 현재 사용자 이미지]를 file 형태로 변경
        File inputImage = decodeImage(faceBiometricsRequest.getEncodedUserFace());

        AmazonS3 amazonS3Client = amazonS3Client();
        // DID가 복호화한 [기존 저장된 사용자 이미지]
        //File savedUserImage = getFile(faceBiometricsRequest.getSavedS3UserFaceUrl());
        // https://blockai-bucket.s3.ap-northeast-2.amazonaws.com/test.png
        String fileName = faceBiometricsRequest.getSavedS3UserFaceUrl().split("/")[3];
        com.amazonaws.services.s3.model.S3Object savedUserImage = amazonS3Client.getObject(new GetObjectRequest(bucket, fileName));

        ByteBuffer sourceImageBytes = null;
        ByteBuffer targetImageBytes = null;

        //Load source and target images and create input parameters
        try (InputStream inputStream = new FileInputStream(inputImage)) {
            sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        } catch (Exception e) {
            System.out.println("Failed to load source image");
            System.exit(1);
        }
//        try (InputStream inputStream = new FileInputStream(savedUserImage)) {
//            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
//        }
        try (InputStream inputStream = savedUserImage.getObjectContent()) {
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

        //파일 삭제
        inputImage.delete();
        
        return result;
    }


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
