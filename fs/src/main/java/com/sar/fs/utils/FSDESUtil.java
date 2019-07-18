package com.sar.fs.utils;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:39
 * @describe
 */
@SuppressLint({"all"})
public class FSDESUtil {
    public static final String DES_KEY_STRING = "ABSujsuu";

    public FSDESUtil() {
    }

    public static byte[] convertHexString(String ss) {
        byte[] digest = new byte[ss.length() / 2];

        for(int i = 0; i < digest.length; ++i) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte)byteValue;
        }

        return digest;
    }

    public static String toHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            String plainText = Integer.toHexString(255 & b[i]);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }

            hexString.append(plainText);
        }

        return hexString.toString();
    }

    public static void main(String[] args) throws Exception {
        String key = "A1B2C3D4E5F60708";
        String source = "amigoxie";
        System.out.println("原文: " + source);
        String encryptData = encrypt(source, key);
        System.out.println("加密后: " + encryptData);
        String decryptData = decrypt(encryptData, key);
        System.out.println("解密后: " + decryptData);
        System.out.println("MD5: " + getMD5("715buyQ"));
    }

    public static String encrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(1, secretKey, iv);
        return encodeBase64(cipher.doFinal(message.getBytes("UTF-8")));
    }

    public static String encodeBase64(byte[] b) {
        return Base64.encodeToString(b, 0);
    }

    public static String decrypt(String message, String key) throws Exception {
        byte[] bytesrc = decodeBase64(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(2, secretKey, iv);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    public static byte[] decodeBase64(String base64String) {
        return Base64.decode(base64String, 0);
    }

    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();
            StringBuffer strBuf = new StringBuffer();

            for(int i = 0; i < encryption.length; ++i) {
                if (Integer.toHexString(255 & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(255 & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(255 & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException var5) {
            return "";
        } catch (UnsupportedEncodingException var6) {
            return "";
        }
    }
}

