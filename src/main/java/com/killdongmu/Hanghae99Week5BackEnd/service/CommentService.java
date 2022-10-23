package com.killdongmu.Hanghae99Week5BackEnd.service;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.CommentRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.CommentListResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.CommentResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.GlobalResDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.ResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Comments;
import com.killdongmu.Hanghae99Week5BackEnd.repository.BoardRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    // 댓글 목록 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getCommentList(Long boardId) {

        Boards boards = checkBoard(boardRepository, boardId);

        CommentListResponseDto commentList = new CommentListResponseDto();

        List<Comments> comments = commentRepository.findAllByOrderByCreatedAtDesc();

        for (Comments comment : comments) {
            if (comment.getBoards().getId().equals(boards.getId())) {
                commentList.addComment(new CommentResponseDto(comment));
            }

        }
        return ResponseDto.success(
                CommentListResponseDto.builder()
                        .commentList(commentList.getCommentList())
                        .build()
        );
    }

    // 댓글 상세 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getComment(Long commentId) {

        Comments comments = checkComment(commentRepository, commentId);

        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comments.getId())
                        .comment(comments.getComment())
                        .createdAt(comments.getCreatedAt())
                        .modifiedAt(comments.getModifiedAt())
                        .build()
        );
    }

    // 댓글 등록
    @Transactional
    public ResponseDto<?> createComment(Long boardId, CommentRequestDto commentRequestDto) {

        Boards boards = checkBoard(boardRepository, boardId);

        Comments comment = Comments.builder()
                .boards(boards)
                .comment(commentRequestDto.getComment())
                .build();

        commentRepository.save(comment);

        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .comment(comment.getComment())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build()
        );
    }

    // 댓글 수정
    @Transactional
    public ResponseDto<?> updateComment(Long commentId, CommentRequestDto commentRequestDto) {

        Comments comment = checkComment(commentRepository, commentId);

        comment.commentUpdate(commentRequestDto);

        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .comment(comment.getComment())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build()
        );
    }

    // 댓글 삭제
    @Transactional
    public GlobalResDto deleteComment(Long commentId){

        Comments comment = checkComment(commentRepository, commentId);

        commentRepository.delete(comment);

        return new GlobalResDto("댓글 삭제 완료", HttpStatus.OK.value());
    }

    private Boards checkBoard(BoardRepository boardRepository, Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new RuntimeException("Not Found Board")
        );
    }

    private Comments checkComment(CommentRepository commentRepository, Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("Not Found Board")
        );
    }

}
