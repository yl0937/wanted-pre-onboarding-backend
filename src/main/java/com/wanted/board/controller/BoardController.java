package com.wanted.board.controller;

import com.wanted.board.common.response.ResponseUtil;
import com.wanted.board.common.response.SuccessResponse;
import com.wanted.board.controller.dto.BoardRequest;
import com.wanted.board.controller.dto.BoardResponse;
import com.wanted.board.domain.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/boards")
    public SuccessResponse<BoardResponse> addBoard(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid BoardRequest boardRequest) {
     BoardResponse boardResponse = boardService.addBoard(boardRequest,userId);
     return ResponseUtil.success(boardResponse);
    }
}
