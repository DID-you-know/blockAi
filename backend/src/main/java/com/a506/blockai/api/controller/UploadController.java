package com.a506.blockai.api.controller;

import com.a506.blockai.common.response.BaseResponseBody;
import com.a506.blockai.common.util.S3Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/image")
@RestController
public class UploadController {

//    private final Uploader uploader;
//
//    @PostMapping("/upload")
//    public String upload(@RequestParam("data") MultipartFile file) throws IOException {
//        log.info("upload");
//        log.info("-------------------" + file.toString());
//        return uploader.uploadS3Instance(file, "static");
//    }
//
//    @PostMapping("/delete")
//    public ResponseEntity<BaseResponseBody> delete(@RequestParam("url") String url) {
//        try {
//            uploader.deleteS3Instance(url);
//            return ResponseEntity.ok(BaseResponseBody.of(200, "파일 삭제 완료"));
//        }catch(Exception e) {
//            return ResponseEntity.ok(BaseResponseBody.of(400, "파일을 찾을 수 없습니다"));
//        }
//    }
    private final S3Upload s3Upload;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws  IOException{
        return s3Upload.upload(multipartFile, "static");
    }
}
