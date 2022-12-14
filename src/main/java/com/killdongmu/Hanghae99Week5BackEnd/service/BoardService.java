package com.killdongmu.Hanghae99Week5BackEnd.service;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.BoardRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardListResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.ResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Comments;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Hearts;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Members;
import com.killdongmu.Hanghae99Week5BackEnd.repository.BoardRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.CommentRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Value("${cloud.aws.s3.dir}")
    private String dir;
    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    private final HeartRepository heartRepository;

    private final S3UploadService s3UploadService;

    public ResponseEntity<?> findBoardList() {

        List<Boards> findBoardList = boardRepository.findAllByOrderByBoardIdDesc();

        List<BoardListResponseDto> boardList = new ArrayList<>();

        for (Boards board : findBoardList) {
            Long countComment = commentRepository.countByBoard(board);
            Long countHeart = heartRepository.countByBoard(board);

            BoardListResponseDto boardListResponseDto = BoardListResponseDto.builder().
                    boardId(board.getBoardId()).
                    title(board.getTitle()).
                    file(board.getFile()).
                    content(board.getContent()).
                    countComment(countComment).
                    countHeart(countHeart).
                    username(board.getMember().getUsername()).
                    createdAt(board.getCreatedAt()).
                    modifiedAt(board.getModifiedAt()).
                    build();

            boardList.add(boardListResponseDto);
        }

        return new ResponseEntity<>(boardList, HttpStatus.OK);

    }

    // ????????? ?????? ???????????? ????????????
    @Transactional
    public ResponseEntity<?> findBoardPageList(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Slice<Boards> boardList = boardRepository.findAllByOrderByBoardIdDesc(pageable);

        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    public ResponseEntity<?> findBoard(Long boardId) {

        Boards findBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("???????????? ???????????? ????????????.")
        );

        List<Hearts> heartList = heartRepository.findAllByBoard(findBoard);

        List<String> heartedUsernameList = new ArrayList<>();

        for (Hearts hearts : heartList) {
            heartedUsernameList.add(hearts.getMember().getUsername());
        }

        BoardResponseDto board = BoardResponseDto.builder().
                board_id(findBoard.getBoardId()).
                title(findBoard.getTitle()).
                file(findBoard.getFile()).
                content(findBoard.getContent()).
                username(findBoard.getMember().getUsername()).
                commentList(findBoard.getCommentList()).
                heartedUsernameList(heartedUsernameList).
                createdAt(findBoard.getCreatedAt()).
                modifiedAt(findBoard.getModifiedAt()).
                build();

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    public ResponseEntity<?> createBoard(BoardRequestDto boardRequestDto, Members members) throws IOException {

        if(!boardRequestDto.getTitle().equals("") || !boardRequestDto.getContent().equals("") || boardRequestDto.getFile().isEmpty())
            return new ResponseEntity<>("???????????? ?????? ???????????? ????????? ???????????? ?????????.", HttpStatus.BAD_REQUEST);

        Boards board = Boards.builder().
                title(boardRequestDto.getTitle()).
                file(s3UploadService.upload(boardRequestDto.getFile(), dir)).
                content(boardRequestDto.getContent()).
                member(members).
                build();

        boardRepository.save(board);

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateBoard(BoardRequestDto boardRequestDto, Long boardId, Members members) {

        // Boards board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException());
        Boards board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("???????????? ???????????? ????????????.")
        );

        if(!boardRequestDto.getTitle().equals("") || !boardRequestDto.getContent().equals("") || boardRequestDto.getFile().isEmpty())
            return new ResponseEntity<>("???????????? ?????? ???????????? ????????? ???????????? ?????????.", HttpStatus.BAD_REQUEST);

        if(!board.getMember().getMemberId().equals(members.getMemberId()))
            return new ResponseEntity<>("???????????? ???????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST);

        board.updateBoard(boardRequestDto.getTitle(), boardRequestDto.getContent());

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteBoard(Long boardId, Members members) {

        // ????????? ????????? ??? ?????? ???????????? ??????, ????????? ??????
        Boards board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("???????????? ???????????? ????????????.")
        );

        List<Comments> commentList = commentRepository.findAllByBoard(board);
        List<Hearts> heartList = heartRepository.findAllByBoard(board);

        if(!board.getMember().getMemberId().equals(members.getMemberId()))
            return new ResponseEntity<>("???????????? ???????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST);

        if (commentList.size() > 0) {
            commentRepository.deleteByBoard(board);
        }

        if (heartList.size() > 0) {
            heartRepository.deleteByBoard(board);
        }

        boardRepository.deleteById(boardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
