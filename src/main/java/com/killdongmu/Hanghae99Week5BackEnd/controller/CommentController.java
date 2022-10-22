package com.killdongmu.Hanghae99Week5BackEnd.controller;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.CommentRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.CommentListResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.CommentResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public List<CommentListResponseDto> commentList() {
        return commentService.findCommentList();
    }

    @GetMapping("/comments/{comment-id}")
    public CommentResponseDto commentInfo(@PathVariable(name = "comment-id") Long commentId) {
        return commentService.findComment(commentId);
    }

    @PostMapping("/comments/create")
    public void createComment(@RequestBody CommentRequestDto commentRequestDto) {
        commentService.createComment(commentRequestDto);
    }

    @PutMapping("/comments/update/{comment-id}")
    public void updateComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable(name = "comment-id") Long commentId) {
        commentService.updateComment(commentRequestDto, commentId);
    }
}
