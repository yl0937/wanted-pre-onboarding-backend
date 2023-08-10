package com.wanted.board.domain.board.entity;

import com.wanted.board.common.entity.BaseEntity;
import com.wanted.board.controller.dto.BoardRequest;
import com.wanted.board.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private User user;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String imageUrl;

    public static Board from(BoardRequest boardRequest, User user) {
        return Board.builder()
                .user(user)
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .imageUrl(boardRequest.getImageUrl())
                .build();
    }

    public void updateBoard(BoardRequest boardRequest) {
        if (boardRequest.getTitle() != null)
            this.title = boardRequest.getTitle();
        if (boardRequest.getContent() != null)
            this.content = boardRequest.getContent();
        if (boardRequest.getImageUrl() != null)
            this.imageUrl = boardRequest.getImageUrl();
    }
}
