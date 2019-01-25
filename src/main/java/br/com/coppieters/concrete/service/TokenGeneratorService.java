package br.com.coppieters.concrete.service;

import br.com.coppieters.concrete.domain.model.User;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;

@Service
public class TokenGeneratorService {

    public String provide(User user) {
        return UUID.randomUUID().toString();
    }
}
