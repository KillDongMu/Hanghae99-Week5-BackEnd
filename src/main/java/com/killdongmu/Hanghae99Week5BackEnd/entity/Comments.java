package com.killdongmu.Hanghae99Week5BackEnd.entity;

import com.killdongmu.Hanghae99Week5BackEnd.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comments extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long comment_id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Boards board;

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
