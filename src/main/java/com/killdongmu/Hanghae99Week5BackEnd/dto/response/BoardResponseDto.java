package com.killdongmu.Hanghae99Week5BackEnd.dto.response;

import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private String title;
    private String content;
    private LocalDateTime createAt;

    public BoardResponseDto(Boards boards){
        this.title = boards.getTitle();
        this.content = boards.getContent();
        this.createAt = boards.getCreatedAt();
    }
}
