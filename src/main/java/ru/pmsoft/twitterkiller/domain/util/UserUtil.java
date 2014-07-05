package ru.pmsoft.twitterkiller.domain.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Вспомогательные методы для работы с классом entity.User
 */
public class UserUtil {
    public static String getSHA256(String password, String salt) {
        String base = password;
        base += salt;
        byte[] buffer = base.getBytes();
        try {
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateSalt() {
        return new BigInteger(130,new Random()).toString(16);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static Date computeExpiration(TimeUnit unit, long duration) {
        return new Date(new Date().getTime() + unit.toMillis(duration));
    }





}


