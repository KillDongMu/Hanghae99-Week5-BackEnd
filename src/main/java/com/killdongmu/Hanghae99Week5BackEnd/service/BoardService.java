package com.killdongmu.Hanghae99Week5BackEnd.service;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.BoardRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardListResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.GlobalResDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.ResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getBoardList() {

        BoardListResponseDto boardList = new BoardListResponseDto();

        List<Boards> boards = boardRepository.findAllByOrderByCreatedAtDesc();

        for (Boards board : boards) {
            boardList.addBoard(new BoardResponseDto(board));
        }

        return ResponseDto.success(
                BoardListResponseDto.builder()
                        .boardList(boardList.getBoardList())
                        .build()
        );
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getBoard(Long boardId) {

        Boards board = checkBoard(boardRepository, boardId);

        return ResponseDto.success(
                BoardResponseDto.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .createdAt(board.getCreatedAt())
                        .modifiedAt(board.getModifiedAt())
                        .build()
        );
    }

    // 게시글 등록
    @Transactional
    public ResponseDto<?> createBoard(BoardRequestDto boardRequestDto) {

        Boards board = Boards.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .build();

        boardRepository.save(board);

        return ResponseDto.success(
                BoardResponseDto.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .createdAt(board.getCreatedAt())
                        .build()
        );
    }

    // 게시글 수정
    @Transactional
    public ResponseDto<?> updateBoard(Long id, BoardRequestDto boardRequestDto) {

        Boards board = checkBoard(boardRepository, id);

        board.boardUpdate(boardRequestDto);

        return ResponseDto.success(
                BoardResponseDto.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .createdAt(board.getCreatedAt())
                        .modifiedAt(board.getModifiedAt())
                        .build()
        );
    }

    // 게시글 삭제
    @Transactional
    public GlobalResDto deleteBoard(Long id) {

        Boards board = checkBoard(boardRepository, id);

        boardRepository.delete(board);

        return new GlobalResDto("게시물 삭제 완료", HttpStatus.OK.value());
    }

    // 게시판 테이블에 게시글 id 존재여부 확인 (공통요소)
    private Boards checkBoard(BoardRepository boardRepository, Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Not Found Board")
        );
    }
}
