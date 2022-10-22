package com.killdongmu.Hanghae99Week5BackEnd.service;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.BoardRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardListResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardListResponseDto> findBoardList() {

        List<Boards> boardList = boardRepository.findAllByOrderByCreatedAtDesc();

        List<BoardListResponseDto> responseBoardList = new ArrayList<>();

        for (Boards board : boardList) {

            BoardListResponseDto boardListResponseDto = BoardListResponseDto.builder()
                    .board_id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdAt(board.getCreatedAt())
                    .modifiedAt(board.getModifiedAt())
                    .build();

            responseBoardList.add(boardListResponseDto);
        }

        return responseBoardList;

    }

    public BoardResponseDto findBoard(Long boardId) {

        Boards board = boardRepository.findById(boardId).orElseThrow();

        BoardResponseDto responseBoard = BoardResponseDto.builder()
                .board_id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .build();

        return responseBoard;
    }

    public void createBoard(BoardRequestDto boardRequestDto) {

        Boards board = Boards.builder().
                title(boardRequestDto.getTitle()).
                content(boardRequestDto.getContent()).
                build();

        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(BoardRequestDto boardRequestDto, Long boardId) {

        Boards board = new Boards();

        board.updateBoard(boardRequestDto.getTitle(), boardRequestDto.getContent());
    }

    public void deleteBoard(Long boardId) {

        boardRepository.deleteById(boardId);
    }
}


