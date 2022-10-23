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
public class CommentListResponseDto {

    List<CommentResponseDto> commentList = new LinkedList<>();

    public void addComment(CommentResponseDto commentResponseDto){
        commentList.add(commentResponseDto);
    }
}
