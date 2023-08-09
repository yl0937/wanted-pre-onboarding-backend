package com.wanted.board.common.jwt;

import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.wanted.board.common.jwt.JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME;


@Slf4j
@Component
public class JwtProvider {

    private final UserDetailsService userDetailsService;

    private final Key secretKey;



    public JwtProvider(
            UserDetailsService userDetailsService,
            @Value("${jwt.secret}") String secretKey
    ) {
        this.userDetailsService = userDetailsService;

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT Access Token 발급
    public TokenDto generateAccessToken(Long userId) {
        Date now = new Date();

        Date accessTokenExpirationIn = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME);
        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(accessTokenExpirationIn)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
                .token(accessToken)
                .tokenExpirationIn(accessTokenExpirationIn)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        String userId = this.getUserId(accessToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(
                Long.parseLong(userId),
                "",
                userDetails.getAuthorities());
    }

    private String getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

}
