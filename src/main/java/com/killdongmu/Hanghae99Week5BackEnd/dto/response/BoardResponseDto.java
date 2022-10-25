package com.killdongmu.Hanghae99Week5BackEnd.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import java.util.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {

    private Long board_id;

    private String title;

    private String content;

    private List<String> commentList;

    private List<String> commentMemberList;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
