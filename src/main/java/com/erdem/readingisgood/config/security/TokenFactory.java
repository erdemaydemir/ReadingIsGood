package com.erdem.readingisgood.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

import static com.erdem.readingisgood.config.security.WebSecurityConfig.EXPIRATION_TIME;
import static com.erdem.readingisgood.config.security.WebSecurityConfig.SECRET;

public class TokenFactory {

    public static String createToken(Authentication auth) {
        return Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }
}
