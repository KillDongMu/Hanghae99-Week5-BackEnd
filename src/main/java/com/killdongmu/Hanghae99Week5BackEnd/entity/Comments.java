package com.killdongmu.Hanghae99Week5BackEnd.entity;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.CommentRequestDto;
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
public class Comments extends Timestamped {

    @Id
    @Column(name = "comment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Boards boards;

    @Column(nullable = false)
    private String comment;

    public void commentUpdate(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }

}
