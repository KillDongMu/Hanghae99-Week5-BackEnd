package com.killdongmu.Hanghae99Week5BackEnd.controller;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.CommentRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.GlobalResDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.ResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class CommentController {

    private final CommentService commentService;

    // 댓글 목록 조회
    @GetMapping("/{board-id}/comments")
    public ResponseDto<?> getCommentList(@PathVariable(name = "board-id") Long boardId){
        return commentService.getCommentList(boardId);
    }

    // 댓글 상세 조회
    @GetMapping("/comments/{comment-id}")
    public ResponseDto<?> getComment(@PathVariable(name = "comment-id") Long commentId){
        return commentService.getComment(commentId);
    }

    // 댓글 등록
    @PostMapping("/{board-id}/comments")
    public ResponseDto<?> createComment(@PathVariable(name = "board-id") Long boardId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.createComment(boardId, commentRequestDto);
    }

    // 댓글 수정
    @PutMapping("/comments/{comment-id}")
    public ResponseDto<?> updateComment(@PathVariable(name = "comment-id") Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        return commentService.updateComment(commentId, commentRequestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{comment-id}")
    public GlobalResDto deleteComment(@PathVariable(name = "comment-id") Long commentId){
        return commentService.deleteComment(commentId);
    }

}
