package com.killdongmu.Hanghae99Week5BackEnd.controller;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.BoardRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardListResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public List<BoardListResponseDto> boardList(){

        // 기존
        /*List<BoardListResponseDto> boardList = boardService.findBoardList();

        return boardList;*/
        return boardService.findBoardList();
    }

    @GetMapping("/boards/{board-id}")
    public BoardResponseDto boardInfo(@PathVariable(name = "board-id") Long boardId){

        return boardService.findBoard(boardId);

    }

    @PostMapping("/boards/create")
    public void createBoard(@RequestBody BoardRequestDto boardRequestDto){
        boardService.createBoard(boardRequestDto);
    }

    @PutMapping("/boards/update/{board-id}")
    public void updateBoard(@RequestBody BoardRequestDto boardRequestDto,
                            @PathVariable(name = "board-id") Long boardId) {
        boardService.updateBoard(boardRequestDto, boardId);
    }

    @DeleteMapping("/boards/delete/{board-id}")
    public void deleteBoard(@PathVariable(name = "board-id") Long boardId) {
        boardService.deleteBoard(boardId);
    }
}
