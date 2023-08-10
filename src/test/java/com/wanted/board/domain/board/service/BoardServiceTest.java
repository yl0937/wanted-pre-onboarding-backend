package com.wanted.board.domain.board.service;

import com.wanted.board.common.exception.BaseException;

import static org.junit.jupiter.api.Assertions.*;

import com.wanted.board.controller.dto.BoardRequest;
import com.wanted.board.controller.dto.BoardResponse;
import com.wanted.board.domain.board.entity.Board;
import com.wanted.board.domain.board.repository.BoardRepository;
import com.wanted.board.domain.user.entity.User;
import com.wanted.board.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 등록 : 성공")
    void addBoard() {
        // given
        User mockUser = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("12345678")
                .build();

        given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(mockUser));
        when(boardRepository.save(any(Board.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        ArgumentCaptor<Board> captor = ArgumentCaptor.forClass(Board.class);

        // when
        BoardRequest boardRequest = BoardRequest.builder()
                .title("test")
                .content("test")
                .build();
        boardService.addBoard(boardRequest, mockUser.getId());

        // then
        verify(boardRepository, times(1)).save(captor.capture());
        assertEquals("test", captor.getValue().getTitle());
    }

    @Test
    @DisplayName("게시글 등록 : 실패")
    void addBoardFail() {
        // given
        BoardRequest boardRequest = BoardRequest.builder()
                .title("test")
                .content("test")
                .build();

        // when, then
        assertThrows(BaseException.class, () -> boardService.addBoard(boardRequest, 1L));
    }

    @Test
    @DisplayName("게시글 수정 : 성공")
    void updateBoard() {
        // given
        User mockUser = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("12345678")
                .build();
        Board mockBoard = Board.builder()
                .id(1L)
                .title("test")
                .content("test")
                .user(mockUser)
                .build();

        given(boardRepository.findById(anyLong())).willReturn(Optional.of(mockBoard));
        when(boardRepository.save(any(Board.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        ArgumentCaptor<Board> captor = ArgumentCaptor.forClass(Board.class);

        // when
        BoardRequest boardRequest = BoardRequest.builder()
                .title("test1")
                .content("test")
                .build();
        boardService.updateBoard(mockBoard.getId(), boardRequest, mockUser.getId());
        // then
        verify(boardRepository, times(1)).save(captor.capture());
        assertEquals("test1", captor.getValue().getTitle());
    }

    @Test
    @DisplayName("게시글 수정 : 없는 게시글")
    void updateVoidBoard() {
        // given
        BoardRequest boardRequest = BoardRequest.builder()
                .title("test")
                .content("test")
                .build();
        User mockUser = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("12345678")
                .build();

        // when, then
        assertThrows(BaseException.class, () -> boardService.updateBoard(1L, boardRequest, mockUser.getId()));
    }

    @Test
    @DisplayName("게시글 수정 : 권한 없음")
    void updateOthersBoard() {
        // given
        User mockUser = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("12345678")
                .build();
        Board mockBoard = Board.builder()
                .id(1L)
                .title("test")
                .content("test")
                .user(mockUser)
                .build();

        given(boardRepository.findById(anyLong())).willReturn(Optional.of(mockBoard));

        // when
        BoardRequest boardRequest = BoardRequest.builder()
                .title("test1")
                .content("test")
                .build();

        // then
        assertThrows(BaseException.class, () -> boardService.updateBoard(mockBoard.getId(), boardRequest, 2L));
    }

    @Test
    @DisplayName("게시글 삭제 : 성공")
    void deleteBoard() {
        // given
        User mockUser = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("12345678")
                .build();
        Board mockBoard = Board.builder()
                .id(1L)
                .title("test")
                .content("test")
                .user(mockUser)
                .build();

        given(boardRepository.findById(anyLong())).willReturn(Optional.of(mockBoard));

        // when
        boardService.deleteBoard(mockBoard.getId(), mockUser.getId());

        // then
        Assertions.assertThat(boardRepository.findById(mockBoard.getId()).isEmpty());
    }

    @Test
    @DisplayName("게시글 삭제 : 실패 (없는 게시글)")
    void deleteVoidBoard() {
        // given
        User mockUser = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("12345678")
                .build();

        // when, then
        assertThrows(BaseException.class, () -> boardService.deleteBoard(1L, mockUser.getId()));
    }

    @Test
    @DisplayName("게시글 삭제 : 실패 (권한 없음)")
    void deleteOthersBoard() {
        // given
        User mockUser = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("12345678")
                .build();
        Board mockBoard = Board.builder()
                .id(1L)
                .title("test")
                .content("test")
                .user(mockUser)
                .build();

        given(boardRepository.findById(anyLong())).willReturn(Optional.of(mockBoard));

        // when, then
        assertThrows(BaseException.class, () -> boardService.deleteBoard(mockBoard.getId(), 2L));
    }

    @Test
    @DisplayName("게시글 조회 : 성공")
    void readBoard() {
        // given
        User mockUser = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("12345678")
                .build();
        Board mockBoard = Board.builder()
                .id(1L)
                .title("test")
                .content("test")
                .user(mockUser)
                .build();
        Board mockBoard1 = Board.builder()
                .id(2L)
                .title("test1")
                .content("test1")
                .user(mockUser)
                .build();

        Page<Board> boardList = new PageImpl<>(List.of(mockBoard, mockBoard1));
        PageRequest pageRequest = PageRequest.of(0, 10);
        given(boardRepository.findAll(pageRequest)).willReturn(boardList);

        // when
        Page<BoardResponse> boardResponsePage = boardService.readBoardList(pageRequest);

        // then
        assertEquals(boardResponsePage.getSize(), 2);

    }


}