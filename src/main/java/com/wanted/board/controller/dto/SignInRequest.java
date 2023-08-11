package com.wanted.board.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class SignInRequest {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
}