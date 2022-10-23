package com.killdongmu.Hanghae99Week5BackEnd.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardListResponseDto {
    List<BoardResponseDto> boardList = new LinkedList<>();

    public void addBoard(BoardResponseDto boardResponseDto){
        boardList.add(boardResponseDto);
    }
}
