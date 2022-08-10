package com.sparta.springweb.model;

import com.sparta.springweb.dto.CommentLikeRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "comment_like")
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public CommentLike(CommentLikeRequestDto requestDto) {
        this.user = requestDto.getUser();
        this.comment = requestDto.getComment();
    }
}
