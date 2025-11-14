package security;

// Andrew :)

/*
    This is a static service.
    It has only two functionalities:
        - encode(String)
            -> returns a hexadecimal string hash for a given string, null if an error occurs
            #  Can be used to store passwords in databases to maintain privacy
        - compare(String, String)
            -> returns true if both given hashes are the same
            #  Can be used as means to verify password entry
*/

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordService {
    private static String convertToSHA256Hash(String password) throws NoSuchAlgorithmException {
        /* Method to convert a string into a hash using SHA256 encryption */
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        // After encryption, it must be converted into a string for returning & storing
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String encode(String password) {
        /* Method to convert a string password into hash */
        /* @param string password */
        /* @return hash as hexadecimal string */
        try {
            return convertToSHA256Hash(password);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("[PasswordService]: Algorithm given is not found, check its spelling");
            return null;
        }
    }

    public static boolean compare(String hash, String other) {
        return hash != null && hash.equals(other);
    }
}
