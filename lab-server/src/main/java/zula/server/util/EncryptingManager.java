package zula.server.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptingManager {

    public String encodeHash(String message) {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest((message).getBytes(StandardCharsets.UTF_8));
            return encoder.encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

}
