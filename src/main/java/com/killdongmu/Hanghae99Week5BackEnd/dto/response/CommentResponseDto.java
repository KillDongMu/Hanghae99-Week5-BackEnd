package com.killdongmu.Hanghae99Week5BackEnd.dto.response;

import com.killdongmu.Hanghae99Week5BackEnd.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long id;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comments comments){
        this.id = comments.getId();
        this.comment = comments.getComment();
        this.createdAt = comments.getCreatedAt();
        this.modifiedAt = comments.getModifiedAt();
    }
}
