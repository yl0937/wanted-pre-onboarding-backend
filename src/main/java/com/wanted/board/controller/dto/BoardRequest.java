package com.wanted.board.controller.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @URL
    private String imageUrl;
}
