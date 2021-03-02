package me.cerratolabs.rust.servermanager;

import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.encryption.SaltService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EncryptionTest {

    @Autowired private SaltService encryptionService;

    @Test
    @SneakyThrows
    void givenString_whenEncrypt_thenSuccess() {
        String input = "erJoseLuizEzLizt30o";
        String s = SaltService.generateRandomSalt();
        String encrypt = encryptionService.encrypt(input);
        String decrypt = encryptionService.decrypt(encrypt);
        Assertions.assertEquals(input, decrypt);
    }

    @Test
    @SneakyThrows
    void pepe() {
        String input = "erJoseLuizEzLizt30oerJoseLuizEzLizt30oerJoseLuizEzLizt30oerJoseLuizEzLizt30oerJoseLuizEzLizt30oerJoseLuizEzLizt30oerJoseLuizEzLizt30o";
        String s = SaltService.generateRandomSalt();
        String encrypt = encryptionService.encrypt(input);
        String decrypt = encryptionService.decrypt(encrypt);
        Assertions.assertEquals(input, decrypt);
    }

}