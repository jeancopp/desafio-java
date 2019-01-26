package br.com.coppieters.concrete.service;

import org.junit.Test;
import org.springframework.security.core.token.Sha512DigestUtils;

import static org.junit.Assert.*;

public class PasswordEncrypterTest {

    @Test
    public void apply() {
        final PasswordEncrypter encrypter = new PasswordEncrypter();
        String encPassword = encrypter.apply("senha");
        assertEquals("b7e94be513e96e8c45cd23d162275e5a12ebde9100a425c4ebcdd7fa4dcd897c", encPassword);
    }
}