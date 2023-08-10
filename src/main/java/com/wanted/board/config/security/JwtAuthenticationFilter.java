package com.wanted.board.config.security;

import com.wanted.board.common.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

import static com.wanted.board.common.jwt.JwtProperties.AUTHORIZATION_HEADER;
import static com.wanted.board.common.jwt.JwtProperties.TOKEN_PREFIX;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) {
        String token = resolveToken(request);

        if (StringUtils.hasText(token)) {
            // 토큰 속 사용자 정보를 통해 만들어진 시큐리티 유저의 Authentication을 시큐리티 컨텍스트에 저장
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new NoSuchElementException("token 정보가 없습니다.");
        }
    }

    // Request Header에서 JWT 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length() + 1);
        }
        return null;
    }
}
