/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.phase2;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64.Decoder;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Abhishek
 */
public class Conversation {

    final String fixedAlgo = "AES/CBC/PKCS5Padding"; 
    final String algo = "AES";
    String key = "1111111111111111";
    byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    IvParameterSpec ivspec = new IvParameterSpec(iv);
    
   
    
    ////////////////////////////////////////////////////////////////////////////
    //
    // This method is used to generate a random key for the encryption and decryption.
    //
    ////////////////////////////////////////////////////////////////////////////
    
    public void generateKeyTemp() throws NoSuchAlgorithmException{
        KeyGenerator generator = KeyGenerator.getInstance(algo);
        SecureRandom secureRandom = new SecureRandom();
        int keySize = 128;
        generator.init(keySize, secureRandom);
        SecretKey secret = generator.generateKey();
        //key = secret;
        System.out.println(key);
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    //
    // This method is used to encrypt input string into cipher text in String
    // format.
    //
    ////////////////////////////////////////////////////////////////////////////
    
    public String encryptString(String input) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
        
        Cipher cipherE = Cipher.getInstance(fixedAlgo);
        byte[] encryptkey = key.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(encryptkey, algo);
        cipherE.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
        byte[] tempEncrypt = cipherE.doFinal(input.getBytes("UTF-8"));
        JCATime object = new JCATime();
        String encryptedText = object.convertBytesToStringEncrypt(tempEncrypt);
        return encryptedText;
        
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // This method is used to decrypt input string into plainText in String
    // format.
    //
    ////////////////////////////////////////////////////////////////////////////
    public String decryptString(String input) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
        
        String plainText = "";
       
        Cipher cipherD = Cipher.getInstance(fixedAlgo);
        byte[] decryptKey = key.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(decryptKey, algo);
        cipherD.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
        JCATime object = new JCATime();
        byte[] cipherText = object.convertBytesToStringDecrypt(input.getBytes("UTF-8"));
        plainText = new String(cipherD.doFinal(cipherText), "UTF-8");
        return plainText;
    }
    
}
