package com.killdongmu.Hanghae99Week5BackEnd.controller;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.BoardRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.security.MemberDetails;
import com.killdongmu.Hanghae99Week5BackEnd.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<?> boardList(){

        // 기존
        /*List<BoardListResponseDto> boardList = boardService.findBoardList();

        return boardList;*/
        return boardService.findBoardList();
    }

    @GetMapping(value = "/detail/{board-id}", produces = "application/json")
    public ResponseEntity<?> boardInfo(@PathVariable(name = "board-id") Long boardId){

        return boardService.findBoard(boardId);

    }

    @PostMapping(value ="/create", consumes = "application/json")
    public ResponseEntity<?> createBoard(@RequestBody BoardRequestDto boardRequestDto,
                                         @AuthenticationPrincipal MemberDetails memberDetails){
        return boardService.createBoard(boardRequestDto, memberDetails.getMember());
    }

    @PutMapping(value ="/update/{board-id}", consumes = "application/json")
    public ResponseEntity<?> updateBoard(@RequestBody BoardRequestDto boardRequestDto,
                            @PathVariable(name = "board-id") Long boardId,
                            @AuthenticationPrincipal MemberDetails memberDetails) {
        return boardService.updateBoard(boardRequestDto, boardId, memberDetails.getMember());
    }

    @DeleteMapping(value ="/delete/{board-id}", consumes = "application/json")
    public ResponseEntity<?> deleteBoard(@PathVariable(name = "board-id") Long boardId,
                                         @AuthenticationPrincipal MemberDetails memberDetails) {
        return boardService.deleteBoard(boardId, memberDetails.getMember());
    }
}