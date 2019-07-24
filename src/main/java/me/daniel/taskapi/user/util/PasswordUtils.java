package me.daniel.taskapi.user.util;



import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class PasswordUtils {
    private static final int ITERATIONS = 10000;
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int KEY_LENGTH = 256;
    private static final int DEFAULT_SALT_LENGTH = 32;

//    public static byte[] generateSalt(final int length) {
//        byte[] bytes = new byte[length];
//        new SecureRandom().nextBytes(bytes);
//        return bytes;
//    }
    public static String generateSalt(final int length) {
        StringBuilder returnValue = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }



    public static String generateSaltString() {
        return generateSalt(DEFAULT_SALT_LENGTH);
    }

    public static String generateSaltString(int length) {
        return generateSalt(length);
    }

    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static String generateSecurePassword(String password, String salt) {
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    public static boolean verifyHashEqual(
        String providedPassword,
        String securedPassword,
        String salt
    ) {
        // Generate New secure password with the same salt
        String newSecurePassword = generateSecurePassword(providedPassword, salt);
        // Check if two passwords are equal
        return newSecurePassword.equalsIgnoreCase(securedPassword);
    }

//    public static void main(String[] args) {
//        String password = "qwerasdf1234!";
//        for (int i = 0; i < 3; i++) {
//            String salt = generateSaltString(32);
//            String sp = generateSecurePassword(password, salt);
//            if (verifyHashEqual(password, sp, salt)) {
//                System.out.println(sp + ", " + salt);
//            }
//        }
//    }
}
