package com.sparta.springweb.dto;

import com.sparta.springweb.model.ReplyLike;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyLikeUserDto {
    private Long userId;

    public ReplyLikeUserDto(ReplyLike replyLike) {
        this.userId = replyLike.getUser().getId();
    }
}
