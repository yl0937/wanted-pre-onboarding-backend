package com.wanted.board.controller;

import com.wanted.board.common.response.ResponseUtil;
import com.wanted.board.common.response.SuccessResponse;
import com.wanted.board.controller.dto.BoardRequest;
import com.wanted.board.controller.dto.BoardResponse;
import com.wanted.board.domain.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping()
    public SuccessResponse<BoardResponse> addBoard(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid BoardRequest boardRequest) {
        BoardResponse boardResponse = boardService.addBoard(boardRequest, userId);
        return ResponseUtil.success(boardResponse);
    }

    @PatchMapping("/{boardId}")
    public SuccessResponse<BoardResponse> updateBoard(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long boardId,
            @RequestBody BoardRequest boardRequest) {
        BoardResponse boardResponse = boardService.updateBoard(boardId, boardRequest, userId);
        return ResponseUtil.success(boardResponse);
    }

    @DeleteMapping("/{boardId}")
    public SuccessResponse<?> deleteBoard(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long boardId) {
        boardService.deleteBoard(boardId, userId);
        return ResponseUtil.success();
    }

    @GetMapping()
    public SuccessResponse<Page<BoardResponse>> readBoardList(
            @RequestParam Integer pageNum
    ) {
        PageRequest pageRequest = PageRequest.of(pageNum, 10);
        Page<BoardResponse> boardResponses = boardService.readBoardList(pageRequest);
        return ResponseUtil.success(boardResponses);
    }

    @GetMapping("/{boardId}")
    public SuccessResponse<BoardResponse> readOneBoard(
            @PathVariable Long boardId
    ) {
        BoardResponse boardResponse = boardService.readBoard(boardId);
        return ResponseUtil.success(boardResponse);
    }
}
