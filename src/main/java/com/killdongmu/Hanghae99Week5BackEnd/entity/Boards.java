package com.killdongmu.Hanghae99Week5BackEnd.entity;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.BoardRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Boards extends Timestamped {

    @Id
    @Column(name = "board_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public Boards(BoardRequestDto boardRequestDto){
        this.title  = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }

    public void boardUpdate(BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle() != null ? boardRequestDto.getTitle() : this.title;
        this.content = boardRequestDto.getContent() != null ? boardRequestDto.getContent() : this.content;
    }
}
