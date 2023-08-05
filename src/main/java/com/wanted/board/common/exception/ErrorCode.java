package com.wanted.board.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, 40009, "입력값 유효성 검사에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
