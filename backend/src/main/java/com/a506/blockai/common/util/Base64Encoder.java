package com.a506.blockai.common.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;

public class Base64Encoder {
    public static String encodeFileToBase64Binary(File imageFile, int flag) throws Exception {
        FileInputStream inputStream = null;
        ByteArrayOutputStream byteOutStream = null;
        try {
            inputStream = new FileInputStream(imageFile);
            byteOutStream = new ByteArrayOutputStream();

            int len;
            byte[] buf = new byte[1024];
            while( (len = inputStream.read( buf )) != -1 ) {
                byteOutStream.write(buf, 0, len);
            }

            byte[] fileArray = byteOutStream.toByteArray();
            String encoded = new String(Base64.encodeBase64(fileArray));
            String type = flag == 0 ? "image/" : "audio/";
            return "data:" + type + ";base64, " + encoded;
        }
        catch( IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            byteOutStream.close();
        }
        return "";
    }

}