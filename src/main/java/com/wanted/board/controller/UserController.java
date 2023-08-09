package com.wanted.board.controller;

import com.wanted.board.common.response.ResponseUtil;
import com.wanted.board.common.response.SuccessResponse;
import com.wanted.board.controller.dto.SignUpRequest;
import com.wanted.board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/users/auth")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public SuccessResponse<?> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest.getEmail(), signUpRequest.getPassword());

        return ResponseUtil.success();
    }
}
