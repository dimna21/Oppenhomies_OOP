package DBpackage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hasher {
    public static String hexToString(byte[] bytes) {
        StringBuilder buff = new StringBuilder();
        for (byte aByte : bytes) {
            int val = aByte;
            val = val & 0xff;
            if (val < 16) buff.append('0');
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }
    public static String getHash(String password) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = digest.digest(password.getBytes());

        return hexToString(hashBytes);

    }
}