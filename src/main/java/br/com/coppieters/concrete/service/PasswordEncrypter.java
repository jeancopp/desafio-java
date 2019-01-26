package br.com.coppieters.concrete.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("PasswordEncrypter")
public class PasswordEncrypter implements Function<String, String> {

    @Override
    public String apply(String password) {
        String encrypt = DigestUtils.sha256Hex(password);
        return encrypt;
    }

}
