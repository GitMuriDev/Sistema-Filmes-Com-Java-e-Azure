package com.Senai.Filmes.Security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtUtil {

    @Value("${jwt.secret}") // secret é o nome original do JWT!
    private String secret; // É o token jwt e esse token normalmente tem um tempo de expiração

    @Value("${jwt.expiration}") // O mesmo vale pra esse.
    private long expiration;


    private SecretKey getChave(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
