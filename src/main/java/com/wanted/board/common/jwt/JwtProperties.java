package com.wanted.board.common.jwt;

public interface JwtProperties {

    long ACCESS_TOKEN_EXPIRATION_TIME = 30 * 60 * 1000L; // Access Token: 30분간 토큰 유효
    String AUTHORIZATION_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer";
}
