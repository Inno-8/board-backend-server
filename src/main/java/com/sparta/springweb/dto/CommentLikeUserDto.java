package com.sparta.springweb.dto;

import com.sparta.springweb.model.CommentLike;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentLikeUserDto {
    private Long userId;

    public CommentLikeUserDto(CommentLike commentLike) {
        this.userId = commentLike.getUser().getId();
    }
}
