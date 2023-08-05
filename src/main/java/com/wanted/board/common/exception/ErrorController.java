package com.wanted.board.common.exception;

import com.wanted.board.common.response.ErrorResponse;
import com.wanted.board.common.response.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ErrorController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        log.warn("message: {}, detail: {}", e.getErrorCode().getMessage(), e.getDetail(), e);
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ResponseUtil.error(e.getErrorCode(), e.getDetail()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> params = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            params.add(error.getField() + ": " + error.getDefaultMessage());
        }
        String errorMessage = String.join(", ", params);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseUtil.error(ErrorCode.VALIDATION_FAILED, errorMessage));
    }
}
