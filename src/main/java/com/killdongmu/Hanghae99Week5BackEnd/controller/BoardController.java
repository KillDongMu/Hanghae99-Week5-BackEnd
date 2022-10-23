package com.killdongmu.Hanghae99Week5BackEnd.controller;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.BoardRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardListResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.GlobalResDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.ResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.repository.BoardRepository;
import com.killdongmu.Hanghae99Week5BackEnd.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 전체 조회
    @GetMapping("/boards")
    public ResponseDto<?> getBoardList() {
        return boardService.getBoardList();
    }

    // 게시글 상세 조회
    @GetMapping("/boards/{board-id}")
    public ResponseDto<?> getBoard(@PathVariable(name = "board-id") Long boardId) {
        return boardService.getBoard(boardId);
    }

    // 게시글 작성
    @PostMapping("/boards")
    public ResponseDto<?> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        return boardService.createBoard(boardRequestDto);
    }

    // 게시글 수정
    @PutMapping("/boards/{board-id}")
    public ResponseDto<?> updateBoard(@PathVariable(name = "board-id") Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.updateBoard(boardId, boardRequestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/boards/{board-id}")
    public GlobalResDto deleteBoard(@PathVariable(name = "board-id") Long boardId) {
        return boardService.deleteBoard(boardId);
    }
}
