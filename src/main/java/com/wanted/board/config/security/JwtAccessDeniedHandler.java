package com.wanted.board.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.board.common.exception.ErrorCode;
import com.wanted.board.common.response.ErrorResponse;
import com.wanted.board.common.response.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ErrorResponse errorResponse = ResponseUtil.error(ErrorCode.FORBIDDEN_REQUEST);
        String responseJson = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().print(responseJson);    // 한글 출력을 위해 getWriter() 사용
    }
}
