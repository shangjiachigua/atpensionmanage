package com.caifu.util;

import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/** 
 * java使用AES加密解密 AES-128-ECB加密 
 * 与mysql数据库aes加密算法通用 
 * 数据库aes+base64 加密解密 
 * 
 *  aes加密 
 *  SELECT HEX(AES_ENCRYPT('123','caifu!@#[9921++]'));
 *  aes解密 
 *  SELECT AES_DECRYPT(UNHEX('D117E360C21B66782935750638A95276'),'caifu!@#[9921++]');;
 *  
 * 	-- base64+aes加密 
 *  SELECT to_base64(AES_ENCRYPT('123','caifu!@#[9921++]'));
 *  -- base64+aes解密 
 *  SELECT AES_DECRYPT(from_base64('0RfjYMIbZngpNXUGOKlSdg=='),'caifu!@#[9921++]');
 * 
 * 
 */  
public class AesMyUtil {

    /*
     * 此处使用AES-128-ECB加密模式，key需要为16位。
     */
    private static String sKey="caifu!@#[9921++]";
    // aes加密  
    public static String Encrypt(String sSrc) throws Exception {
        if (sKey == null) {  
            System.out.print("Key为空null");  
            return null;  
        }  
        // 判断Key是否为16位  
        if (sKey.length() != 16) {  
            System.out.print("Key长度不是16位");  
            return null;  
        }  
        byte[] raw = sKey.getBytes("utf-8");  
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"  
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);  
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));  
        return DatatypeConverter.printHexBinary(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。  
    } 
    
    // base64+aes加密  
    public static String EncryptBase64(String sSrc) throws Exception {
        if (sKey == null) {  
            System.out.print("Key为空null");  
            return null;  
        }  
        // 判断Key是否为16位  
        if (sKey.length() != 16) {  
            System.out.print("Key长度不是16位");  
            return null;  
        }  
        byte[] raw = sKey.getBytes("utf-8");  
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"  
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);  
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));  
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。  
    }  
    
    // aes解密  
    public static String Decrypt(String sSrc) throws Exception {
        try {  
            // 判断Key是否正确  
            if (sKey == null) {  
                System.out.print("Key为空null");  
                return null;  
            }  
            // 判断Key是否为16位  
            if (sKey.length() != 16) {  
                System.out.print("Key长度不是16位");  
                return null;  
            }  
            byte[] raw = sKey.getBytes("utf-8");  
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);  
            byte[] encrypted1 = Hex.decodeHex(sSrc.toCharArray());//先用base64解密  
            try {  
                byte[] original = cipher.doFinal(encrypted1);  
                String originalString = new String(original,"utf-8");  
                return originalString;  
            } catch (Exception e) {  
                System.out.println(e.toString());  
                return null;  
            }  
        } catch (Exception ex) {  
            System.out.println(ex.toString());  
            return null;  
        }  
    } 
   
    // base64+aes解密  
    public static String DecryptBase64(String sSrc) throws Exception {
        try {  
            // 判断Key是否正确  
            if (sKey == null) {  
                System.out.print("Key为空null");  
                return null;  
            }  
            // 判断Key是否为16位  
            if (sKey.length() != 16) {  
                System.out.print("Key长度不是16位");  
                return null;  
            }  
            byte[] raw = sKey.getBytes("utf-8");  
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);  
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密  
            try {  
                byte[] original = cipher.doFinal(encrypted1);  
                String originalString = new String(original,"utf-8");  
                return originalString;  
            } catch (Exception e) {  
                System.out.println(e.toString());  
                return null;  
            }  
        } catch (Exception ex) {  
            System.out.println(ex.toString());  
            return null;  
        }  
    }  
    
   
   
    
   
    public static void main(String[] args) throws Exception {  

        // 需要加密的字串  
        String cSrc = "3541325135";
        System.out.println(cSrc);  
        
//        // base64+aes加密
//        String enString = AesMyUtil.EncryptBase64(cSrc);
//        System.out.println("加密后的字串是：" + enString);
//        // base64+aes解密
//        String DeString = AesMyUtil.DecryptBase64(enString);
//        System.out.println("解密后的字串是：" + DeString);
        
        // aes加密  
        String enString2 = AesMyUtil.Encrypt(cSrc);
        System.out.println("加密后的字串是：" + enString2);  
        // aes解密  
        String DeString2 = AesMyUtil.Decrypt(enString2);
        System.out.println("解密后的字串是：" + DeString2); 
    }  
}
