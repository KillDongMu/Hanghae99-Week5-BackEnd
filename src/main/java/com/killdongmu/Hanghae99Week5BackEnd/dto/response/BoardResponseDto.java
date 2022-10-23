package com.killdongmu.Hanghae99Week5BackEnd.dto.response;

import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Boards boards){
        this.id = boards.getId();
        this.title = boards.getTitle();
        this.content = boards.getContent();
        this.createdAt = boards.getCreatedAt();
        this.modifiedAt = boards.getModifiedAt();
    }
}
