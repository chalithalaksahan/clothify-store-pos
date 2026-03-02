package util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class PasswordUtil {
    private static final StandardPBEStringEncryptor encryptor;

    static {
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("iCET");
        encryptor.setAlgorithm("PBEWithMD5AndDES");
         }
        public static String encrypt(String plainPassword){
            return encryptor.encrypt(plainPassword);
        }

        public static boolean check(String plainPassword, String encryptedPassword) {
            return encryptor.decrypt(encryptedPassword).equals(plainPassword);
        }


}

