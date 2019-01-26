package br.com.coppieters.concrete.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenGeneratorServiceTest {

    @Test
    public void provide() {
        TokenGeneratorService generator = new TokenGeneratorService();

        for(int i =0; i < 300; i++) {
            final String token = generator.provide(null);
            assertTrue(token.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"));
        }
    }
}