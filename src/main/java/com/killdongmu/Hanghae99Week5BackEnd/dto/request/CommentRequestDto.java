package com.killdongmu.Hanghae99Week5BackEnd.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
