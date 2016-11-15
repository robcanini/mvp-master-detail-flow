package it.miriade.corsi.android.mvp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by roberto on 15/11/16 for project MVP
 */

public class StringUtils {

    public static String sha256(String source) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(source.getBytes());
            byte byteData[] = md.digest();

            StringBuilder hexString = new StringBuilder();

            for (int i=0; i < byteData.length; i++) {
                String hex=Integer.toHexString(0xff & byteData[i]);
                if(hex.length()==1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

}
