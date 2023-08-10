package com.wanted.board.controller.dto;

import com.wanted.board.common.jwt.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;
    private LocalDateTime accessTokenExpirationDateTime;

    public static TokenResponse fromAccessToken(TokenDto accessToken) {
        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpirationDateTime(accessToken.getTokenExpirationDateTime())
                .build();
    }
}
