package com.killdongmu.Hanghae99Week5BackEnd.controller;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.CommentRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/boards/{board-id}/comments")
    public ResponseEntity<?> commentList(@PathVariable(name = "board-id") Long boardId) {
        return commentService.findCommentList(boardId);
    }

    @GetMapping("/boards/comments/{comment-id}")
    public ResponseEntity<?> commentInfo(@PathVariable(name = "comment-id") Long commentId) {
        return commentService.findComment(commentId);
    }

    @PostMapping("/boards/{board-id}/comments/create")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto commentRequestDto,
                              @PathVariable(name = "board-id") Long boardId) {
        return commentService.createComment(commentRequestDto, boardId);
    }

    @PutMapping("/boards/comments/update/{comment-id}")
    public ResponseEntity<?> updateComment(@RequestBody CommentRequestDto commentRequestDto,
                              @PathVariable(name = "comment-id") Long commentId) {
        return commentService.updateComment(commentRequestDto, commentId);
    }

    @DeleteMapping("/boards/comments/delete/{comment-id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "comment-id") Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
