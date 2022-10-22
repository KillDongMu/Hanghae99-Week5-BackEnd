package com.killdongmu.Hanghae99Week5BackEnd.service;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.CommentRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.CommentListResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.CommentResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Comments;
import com.killdongmu.Hanghae99Week5BackEnd.repository.BoardRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    public ResponseEntity<?> findCommentList(Long boardId) {

        List<Comments> requestCommentList = commentRepository.findCommentsByBoardOrderByCreatedAtDesc(boardId);
        List<CommentListResponseDto> commentList = new ArrayList<>();

        for (Comments comment : requestCommentList) {
            CommentListResponseDto commentListResponseDto = CommentListResponseDto.builder().
                    comment(comment.getComment()).
                    build();

            commentList.add(commentListResponseDto);
        }

        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    public ResponseEntity<?> findComment(Long commentId) {

        Comments requestComment = commentRepository.findById(commentId).orElse(null);

        CommentResponseDto comment = CommentResponseDto.builder().
                comment(requestComment.getComment()).
                createdAt(requestComment.getCreatedAt()).
                modifiedAt(requestComment.getModifiedAt()).
                build();

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    public ResponseEntity<?> createComment(CommentRequestDto commentRequestDto, Long boardId) {

        Comments comment = Comments.builder().
                comment(commentRequestDto.getComment()).
                board(boardRepository.findById(boardId).orElseThrow(RuntimeException::new)).
                build();

        commentRepository.save(comment);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateComment(CommentRequestDto commentRequestDto, Long commentId) {

        Comments comment = commentRepository.findById(commentId).orElseThrow((NullPointerException::new));

        comment.updateComment(commentRequestDto.getComment());

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteComment(Long commentId) {

        commentRepository.deleteById(commentId);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
