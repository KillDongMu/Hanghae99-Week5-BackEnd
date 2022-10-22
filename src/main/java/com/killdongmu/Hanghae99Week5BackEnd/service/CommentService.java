package com.killdongmu.Hanghae99Week5BackEnd.service;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.CommentRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.CommentListResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.CommentResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Comments;
import com.killdongmu.Hanghae99Week5BackEnd.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    public List<CommentListResponseDto> findCommentList() {

        List<Comments> commentList = commentRepository.findAll();
        List<CommentListResponseDto> responseComment = new ArrayList<>();

        for (Comments comment : commentList) {
            CommentListResponseDto commentListResponseDto = CommentListResponseDto.builder().
                    comment(comment.getComment()).
                    build();

            responseComment.add(commentListResponseDto);
        }

        return responseComment;
    }

    public CommentResponseDto findComment(Long commentId) {

        Comments comment = commentRepository.findById(commentId).orElse(null);

        CommentResponseDto responseComment = CommentResponseDto.builder().
                comment(comment.getComment()).
                createdAt(comment.getCreatedAt()).
                modifiedAt(comment.getModifiedAt()).
                build();

        return responseComment;
    }

    public void createComment(CommentRequestDto commentRequestDto) {

        Comments comment = Comments.builder().
                comment(commentRequestDto.getComment()).
                build();

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(CommentRequestDto commentRequestDto, Long commentId) {



    }
}
