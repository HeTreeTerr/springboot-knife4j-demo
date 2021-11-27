package com.geekplus.rms.analysis.common.util.security;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
    private static final String CRYPT_METHOD = "AES";
    private static final byte[] IV = "0000000000000000".getBytes();

    /**
     * AES加密后再base64
     *
     * @param content
     * @return
     */
    public static String encrypt(String content, String password) {
        return encrypt(content, getKey(password));
    }

    private static String encrypt(String content, byte[] bKey) {
        try {
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(IV);
            SecretKeySpec secretKey = new SecretKeySpec(bKey, CRYPT_METHOD);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            byte[] byteContent = padString(content).getBytes("utf-8");
            byte[] result = cipher.doFinal(byteContent);

            return new String(Base64.encode(result, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String content, String password) {
        return decrypt(content, getKey(password));
    }

    private static String decrypt(String content, byte[] bKey) {
        String result = null;
        try {
            byte[] bContent = Base64.decode(content, Base64.DEFAULT);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(IV);
            SecretKeySpec secretKey = new SecretKeySpec(bKey, CRYPT_METHOD);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] bytes = cipher.doFinal(bContent);
            return new String(bytes).trim();
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    private static String padString(String source) {
        char paddingChar = ' ';
        int size = 16;
        int x;
        try {
            x = source.getBytes("UTF-8").length % size;
        } catch (UnsupportedEncodingException e) {
            x = source.length();
        }
        int padLength = size - x;
        StringBuilder sb = new StringBuilder(source);
        for (int i = 0; i < padLength; i++) {
            sb.append(paddingChar);
        }
        return sb.toString();
    }

    public static String MD5(String input) {
        String result = input;
        if (input != null) {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
                md.update(input.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                result = hash.toString(16);
                while (result.length() < 32) {
                    result = "0" + result;
                }
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return result;
    }

    public static byte[] getKey(String password) {
        if (password == null || password.isEmpty()) {
            password = "";
        }
        return MD5(password).getBytes();
    }
}