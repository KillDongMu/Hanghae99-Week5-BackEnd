package com.killdongmu.Hanghae99Week5BackEnd.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MypageResponseDto {


    private String username;
    private Long member_id;
    private String email;
    private List<String> writtenBoardList;
    private List<String> writtenCommentList;
    private List<String> heartList;
}
