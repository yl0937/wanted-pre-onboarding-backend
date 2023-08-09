package com.wanted.board.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, 40001, "입력값 유효성 검사에 실패하였습니다."),
    ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, 40002, "이미 가입된 이메일 입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, 40003, "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 40004, "사용자를 찾을 수 없습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, 40005, "잘못된 토큰입니다."),
    ACCESS_TOKEN_OMISSION(HttpStatus.UNAUTHORIZED, 40101, "인증 정보(액세스 토큰)가 누락되었습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, 40102, "만료된 액세스 토큰입니다."),
    FORBIDDEN_REQUEST(HttpStatus.FORBIDDEN, 40300, "해당 요청에 대한 권한이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "예상치 못한 오류가 발생했습니다.");


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
