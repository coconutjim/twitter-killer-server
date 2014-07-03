package domain.util;

import domain.entity.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

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
        //TODO: salt
        return null;
    }

    public static String generateToken() {
        //TODO: token
        return null;
    }



}


