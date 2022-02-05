package me.cerratolabs.rust.servermanager.encryption;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Random;

import static java.util.Base64.getEncoder;

@Lazy
@Component
@RequiredArgsConstructor
public class SaltService {

    @Autowired private EncryptionService encryptionService;

    private final String salt;
    private int saltLength;

    private String encryptedSalt;
    private int saltEncryptLength;

    @PostConstruct
    public void postConstructor() {
        encryptedSalt = encryptionService.encrypt(salt);
        saltEncryptLength = encryptedSalt.length();
        saltLength = salt.length();
    }

    public String encrypt(String input) {
        String firstPhase = encryptionService.encrypt(input + encryptedSalt);
        return encryptionService.encrypt(encryptedSalt + firstPhase + salt);
    }

    public String decrypt(String input) {
        String firstPhase = encryptionService.decrypt(input);
        int firstPhaseLength = firstPhase.length();

        String secondPhase = firstPhase.substring(saltEncryptLength, firstPhaseLength - saltLength);
        String lastPhase = encryptionService.decrypt(secondPhase);
        return lastPhase.substring(0, lastPhase.length() - saltEncryptLength);
    }

    public static String generateRandomSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[16];
        r.nextBytes(salt);
        return getEncoder().encodeToString(salt);
    }

}