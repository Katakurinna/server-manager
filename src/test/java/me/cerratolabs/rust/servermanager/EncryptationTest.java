package me.cerratolabs.rust.servermanager;

import me.cerratolabs.rust.servermanager.encryption.EncryptionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EncryptationTest {

    @Test
    void givenString_whenEncrypt_thenSuccess()
        throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException {
        EncryptionService encryptionService = new EncryptionService();
        String input = "baeldung";
        SecretKey key = encryptionService.getKeyFromPassword("contraseña", "45645654");
        IvParameterSpec ivParameterSpec = encryptionService.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = encryptionService.encrypt(algorithm, input, key, ivParameterSpec);
        String plainText = encryptionService.decrypt(algorithm, cipherText, key, ivParameterSpec);
        Assertions.assertEquals(input, plainText);
    }

}