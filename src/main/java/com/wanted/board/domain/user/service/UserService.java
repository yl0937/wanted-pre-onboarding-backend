package com.wanted.board.domain.user.service;

import com.wanted.board.common.exception.BaseException;
import com.wanted.board.common.exception.ErrorCode;
import com.wanted.board.controller.dto.SignInRequest;
import com.wanted.board.domain.user.entity.User;
import com.wanted.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private String passwordEncode(String password) {
        return passwordEncoder.encode(password);
    }

    public void signUp(String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new BaseException(ErrorCode.ALREADY_EXISTS_EMAIL);
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncode(password))
                .build();

        userRepository.save(user);
    }

    public Long signIn(SignInRequest signInRequest) {

        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        String password = user.getPassword();

        if (!passwordEncoder.matches(signInRequest.getPassword(), password)) {
            throw new BaseException(ErrorCode.WRONG_PASSWORD);
        }

        return user.getId();
    }
}
