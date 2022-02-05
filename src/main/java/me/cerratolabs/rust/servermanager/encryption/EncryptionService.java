package me.cerratolabs.rust.servermanager.encryption;

import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.config.RustConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import static java.util.Base64.Decoder;
import static java.util.Base64.Encoder;

@Service
public class EncryptionService {

    private final short AES_KEY_BLOCK_SIZE = 16;
    private final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    private Cipher encrypter;
    private Cipher decrypter;

    private Encoder encoder;
    private Decoder decoder;

    @Autowired
    @SneakyThrows
    public EncryptionService(RustConfig config) {
        byte[] passwordByte = config.getSecretKey().getBytes(StandardCharsets.UTF_8);
        MessageDigest instance = MessageDigest.getInstance("SHA-1");
        byte[] digest = instance.digest(passwordByte);
        byte[] bytes = Arrays.copyOf(digest, AES_KEY_BLOCK_SIZE);
        SecretKeySpec secretKey = new SecretKeySpec(bytes, "AES");

        encrypter = Cipher.getInstance(AES_ALGORITHM);
        encrypter.init(Cipher.ENCRYPT_MODE, secretKey);

        decrypter = Cipher.getInstance(AES_ALGORITHM);
        decrypter.init(Cipher.DECRYPT_MODE, secretKey);

        encoder = Base64.getEncoder();
        decoder = Base64.getDecoder();
    }

    @SneakyThrows
    public String decrypt(String input) {
        return new String(decrypter.doFinal(decoder.decode(input)));
    }

    @SneakyThrows
    public String encrypt(String input) {
        byte[] bytes = encrypter.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return encoder.encodeToString(bytes);
    }

}