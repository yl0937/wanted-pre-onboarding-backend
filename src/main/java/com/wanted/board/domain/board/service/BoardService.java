package com.wanted.board.domain.board.service;

import com.wanted.board.common.exception.BaseException;
import com.wanted.board.common.exception.ErrorCode;
import com.wanted.board.controller.dto.BoardRequest;
import com.wanted.board.controller.dto.BoardResponse;
import com.wanted.board.domain.board.entity.Board;
import com.wanted.board.domain.board.repository.BoardRepository;
import com.wanted.board.domain.user.entity.User;
import com.wanted.board.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    @Transactional
    public BoardResponse addBoard(BoardRequest boardRequest, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(ErrorCode.USER_NOT_FOUND));
        return BoardResponse.from(boardRepository.save(Board.from(boardRequest,user)));
    }

    @Transactional
    public BoardResponse updateBoard(Long boardId, BoardRequest request, Long userId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BaseException(ErrorCode.WRONG_BOARD));
        if (!userId.equals(board.getUser().getId())) {
            throw new BaseException(ErrorCode.FORBIDDEN_REQUEST,"게시판 작성자가 아닙니다.");
        }
        board.updateBoard(request);
        return BoardResponse.from(boardRepository.save(board));
    }
}
