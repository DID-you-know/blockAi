package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.VoiceBiometricsRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.java_websocket.util.Base64;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


@Getter
@Setter
@Component
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService{

    private final String accessKey ;
    final private String endPoint = "https://westus.api.cognitive.microsoft.com/";

    @Override
    public String createProfile() {

        System.out.println("key"+accessKey);

        String profileId = "";

        //http통신
        HttpURLConnection conn = null;
        URL url = null;
        //http통신 후 응답 받기 위한 변수
        BufferedReader br = null;
        StringBuffer sb = null;
        String returnData = "";

        //request body
        String requestBody = "{\"locale\" : \"en-us\"}";
        //나머지 url
        String resUrl = endPoint+"/speaker/verification/v2.0/text-independent/profiles";

        try{
            url = new URL(resUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
            conn.setDoOutput(true); //OutputStream을 사용해서 post body 데이터 전송
            try(OutputStream os = conn.getOutputStream()){
                byte request_data[] = requestBody.getBytes("utf-8");
                os.write(request_data);
                os.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            //http 요청
            conn.connect();
            //http 요청 후 응답 받은 데이터를 버퍼에 쌓는다
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            sb = new StringBuffer();
            sb.append(br.readLine());

            //메소드 호출 완료 시 반환하는 변수에 버퍼 데이터 삽입 실시
            returnData = sb.toString();

            //http 요청 응답 코드 확인 실시
            String responseCode = String.valueOf(conn.getResponseCode());

            if(responseCode.equals("201")){ //등록 오류없이 완료인경우 profileId 넘기기
                String[] returnArr = returnData.split("\"");
                int idx = Arrays.asList(returnArr).indexOf("profileId");
                profileId = returnArr[idx+2]; //profileId : 에서 :이 다음배열에 찍혀서 그 다음 배열 선택
            }else{ //등록할 때 오류있으면 무조건 null로 넘기기
                return null;
            }

            System.out.println("http 응답 코드 : "+responseCode);
            System.out.println("http 응답 데이터 : "+returnData);

        }catch(Exception e){
            e.printStackTrace();
        } finally {
            //http 요청 및 응답 완료 후 BufferedReader를 닫아줍니다
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return profileId;
    }

    @Override
    public String enrollment(String voiceId, VoiceBiometricsRequest voiceBiometricsRequest) {

        //http통신
        HttpURLConnection conn = null;
        URL url = null;
        //http통신 후 응답 받기 위한 변수
        BufferedReader br = null;
        StringBuffer sb = null;
        String returnData = "";
        //최종 응답코드
        String responseCode="";

        //나머지 url
        String resUrl = endPoint+"speaker/verification/v2.0/text-independent/profiles/"+voiceId+"/enrollments";

        try{
            url = new URL(resUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "audio/wav; codecs=audio/pcm");
            conn.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
            conn.setDoOutput(true); //OutputStream을 사용해서 post body 데이터 전송

//            File file = new File("/Users/zsoo/Downloads/APPOINTMENTQ1.wav");
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
//
//            int read;
//            byte[] buff = new byte[1024];
//            while ((read = in.read(buff)) > 0)
//            {
//                out.write(buff, 0, read);
//            }
//            out.flush();
//            byte[] audioBytes = out.toByteArray();

            //base64로 변환된 wav파일 decode
            byte[] audioBytes = Base64.decode(voiceBiometricsRequest.getVoice(),0);

            OutputStream os = conn.getOutputStream();
            os.write(audioBytes);
            os.flush();
            os.close();

            //http 요청
            conn.connect();
            //http 요청 후 응답 받은 데이터를 버퍼에 쌓는다
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            sb = new StringBuffer();
            sb.append(br.readLine());

            //메소드 호출 완료 시 반환하는 변수에 버퍼 데이터 삽입 실시
            returnData = sb.toString();

            //http 요청 응답 코드 확인 실시
            responseCode = String.valueOf(conn.getResponseCode());
            System.out.println("http 응답 코드 : "+responseCode);
            System.out.println("http 응답 데이터 : "+returnData);

        }catch(Exception e){
            e.printStackTrace();
        } finally {
            //http 요청 및 응답 완료 후 BufferedReader를 닫아줍니다
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return responseCode;
    }

    @Override
    public float identify(String voiceId, VoiceBiometricsRequest voiceBiometricsRequest) {

        //최종 유사도 점수
        float score = 0;

        //http통신
        HttpURLConnection conn = null;
        URL url = null;

        //http통신 후 응답 받기 위한 변수
        String responseData = "";
        BufferedReader br = null;
        StringBuffer sb = null;
        String returnData = "";

        //나머지 url
        String resUrl = endPoint+"speaker/verification/v2.0/text-independent/profiles/"+voiceId+"/verify";

        try{
            url = new URL(resUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "audio/wav; codecs=audio/pcm");
            conn.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
            conn.setDoOutput(true); //OutputStream을 사용해서 post body 데이터 전송

            //base64로 변환된 wav파일 decode
            byte[] audioBytes = Base64.decode(voiceBiometricsRequest.getVoice(),0);

            OutputStream os = conn.getOutputStream();
            os.write(audioBytes);
            os.flush();
            os.close();

            //http 요청
            conn.connect();
            //http 요청 후 응답 받은 데이터를 버퍼에 쌓는다
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            sb = new StringBuffer();
            sb.append(br.readLine());

            //메소드 호출 완료 시 반환하는 변수에 버퍼 데이터 삽입 실시
            returnData = sb.toString();

            String[] returnArr = returnData.split("\"");
            int idx = Arrays.asList(returnArr).indexOf("score");
            //점수 출력
            String tmpScore = returnArr[idx+1].replaceAll(":","").replaceAll("},","");
            score = Float.parseFloat(tmpScore);

            //http 요청 응답 코드 확인 실시
            String responseCode = String.valueOf(conn.getResponseCode());
            System.out.println("http 응답 코드 : "+responseCode);
            System.out.println("http 응답 데이터 : "+returnData);

        }catch(Exception e){
            e.printStackTrace();
        } finally {
            //http 요청 및 응답 완료 후 BufferedReader를 닫아줍니다
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return score;
    }

}
