package com.killdongmu.Hanghae99Week5BackEnd.dto.response;

import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class BoardListResponseDto {
    List<BoardResponseDto> boardList = new LinkedList<>();

    public void addBoard(BoardResponseDto boardResponseDto){
        boardList.add(boardResponseDto);
    }
}
