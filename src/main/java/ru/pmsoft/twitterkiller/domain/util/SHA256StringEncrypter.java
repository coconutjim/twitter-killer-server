package ru.pmsoft.twitterkiller.domain.util;


import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHA256StringEncrypter implements StringEncrypter {
    @Override
    public String encrypt(String value) throws GeneralSecurityException{
        try {
            return getSHA256(value);
        }catch(NoSuchAlgorithmException e){
            throw new GeneralSecurityException("Failed to encrypt password");
        }
    }


    public static String getSHA256(String password) throws NoSuchAlgorithmException {
        byte[] buffer = password.getBytes();

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(buffer);
            byte[] bytes = md.digest();
            String hex = "";
            for (byte digest : bytes) {
                int b = digest & 0xff;
                if (Integer.toHexString(b).length() == 1) hex = hex + "0";
                hex = hex + Integer.toHexString(b);
            }
            return hex;
    }
}
