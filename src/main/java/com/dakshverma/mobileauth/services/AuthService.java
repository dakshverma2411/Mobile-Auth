/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dakshverma.mobileauth.services;

import com.dakshverma.mobileauth.exceptions.InvalidOtpException;
import com.dakshverma.mobileauth.exceptions.OtpTimeoutException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Daksh
 */
@Service
public class AuthService {
    
    @Value("${otp.timeout}")
    private String timeoutStr;
    
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    
    @Value("${otp.secretkey}")
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;
    
    private void initCrypto() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
    }
    
    
    public String encrypt(String unencryptedString) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        initCrypto();
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.encodeBase64(encryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }
    
    public String decrypt(String encryptedString) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        initCrypto();
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decodeBase64(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
    
    
    public String generateOtpHash(String otp, String phoneNumber) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException{
        Integer timeout = Integer.valueOf(timeoutStr);
        Date curr = new Date();
        long utc = curr.getTime();
        long timeoututc = utc+timeout*1000;
        
        return encrypt(otp+","+phoneNumber+","+timeoututc);
    }
    
    public void validateOtp(String hash, String phone, String otp) throws InvalidOtpException, OtpTimeoutException{
        String decrypted;
        String otpInDecrypted;
        String phoneInDecrypted;
        Long timeOutInDecrypted;
        try{
            decrypted = decrypt(hash);
            otpInDecrypted = decrypted.split(",")[0];
            phoneInDecrypted = decrypted.split(",")[1];
            timeOutInDecrypted = Long.parseLong(decrypted.split(",")[2]);
        }
        catch(Exception e){
            throw new InvalidOtpException();
        }
        
        if(decrypted.substring(0, 5) == null ? otp != null : !decrypted.substring(0, 6).equals(otp)){
            throw new InvalidOtpException();
        }
        
        if(phone == null ? phoneInDecrypted != null : !phone.equals(phoneInDecrypted)){
            throw new InvalidOtpException();
        }
        
        long utc = new Date().getTime();
        if(timeOutInDecrypted<utc){
            throw new OtpTimeoutException();
        }
        
        
    }
    
}
