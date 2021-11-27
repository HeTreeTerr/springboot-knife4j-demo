package com.geekplus.rms.analysis.common.util.security;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Encoder;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * 加解密算法：RSA-2048
 * 填充模式：RSA/ECB/PKCS1Padding
 * RSA最大加密明文大小：245
 * RSA最大解密密文大小：256
 * 签名算法：SHA256withRSA
 *
 * @author mizen
 * @create 2021/3/27
 * @since 1.0.0
 */
public class RSAUtil {

    private static String publicKey = "";
    private static String privateKey = "";

    /**
     * 填充模式
     */
    private final static String CYPHER = "RSA/ECB/PKCS1Padding";
    /**
     * RSA最大加密明文大小
     */
    private static final Integer MAX_ENCRYPT_BLOCK = 245;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    /**
     * 加解密算法长度
     */
    private final static int CRYPTO_BITS = 2048;

    /**
     * 签名算法
     */
    private final static String SIGNATURE = "SHA256withRSA";

    /**
     * 获取密钥对
     *
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(CRYPTO_BITS);
        return generator.generateKeyPair();
    }


    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥
     * @return
     */
    public String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(CYPHER);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }

    /**
     * RSA解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥
     * @return
     */
    public String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(CYPHER);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容

        return new String(decryptedData, "UTF-8");
    }

    /**
     * 签名
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE);
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return 是否验签通过
     */
    public boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE);
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    public static void main1(String[] args) {
        try {
            RSAUtil util = new RSAUtil();
            // 生成密钥对
            KeyPair keyPair = getKeyPair();
            String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
            String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
            System.out.println("私钥:" + privateKey);
            System.out.println("公钥:" + publicKey);
            String data = "内容";
            // RSA加密
            String encryptData = util.encrypt(data, util.getPublicKey(publicKey));
            System.out.println("加密后内容:" + encryptData);
            // RSA解密
            String decryptData = util.decrypt(encryptData, util.getPrivateKey(privateKey));
            System.out.println("解密后内容:" + decryptData);

            // RSA签名
            String sign = util.sign(data, util.getPrivateKey(privateKey));
            // RSA验签
            boolean result = util.verify(data, util.getPublicKey(publicKey), sign);
            System.out.print("验签结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }

    public static void main2(String[] args) {
        try {
            String data = "18611321158";
            int a =0;

            RSAUtil util = new RSAUtil();
            String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6TaDx+ZVjz7xwZ/vPkV5k0TNBT3YVB3O\n" + "k2hMzXUxl5h4zail1neRRWoA1FMQYrW/A/9IOu23Jg0rTkISogKKukj9vBr2AxPKj2pkv1tk1N/6\n" + "zkgJyICCZvdVRUebepA4mxH+4OnuD3cpCop9gcJPTmDWSFLDlZfVmP4ZCoewCV5HoJoRSf67GoWW\n" + "vPk2lAs+dqVHNEImubFvxYZeD4TFp9lbEBLgRXGDrrMbyuf//Ca5kTSzHqkkfAcPqcc8Y8M322lo\n" + "rztp3+DagItEyBkwtNd/+43yBLFh9SqLKDnOuKm9/0NG0adTTKo20DRTrvu1gG/XM8GE5wxfZG4a\n" + "zQ6NPQIDAQAB";
            // RSA加密
            String encryptData = util.encrypt(data, util.getPublicKey(publicKey));
            System.out.println("加密后内容:" + encryptData);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }

    public static void main(String[] args) throws Exception{

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate)cf.generateCertificate(new FileInputStream("/Users/shaomingzhao/Desktop/场景消息/vivo_服务号RSA.crt"));
        PublicKey publicKey = cert.getPublicKey();
        BASE64Encoder base64Encoder=new BASE64Encoder();
        String publicKeyString = base64Encoder.encode(publicKey.getEncoded());
        System.out.println("-----------------公钥--------------------");
        System.out.println(publicKeyString);
        System.out.println("-----------------公钥--------------------");
    }
}


