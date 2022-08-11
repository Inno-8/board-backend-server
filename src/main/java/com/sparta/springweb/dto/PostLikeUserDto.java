package com.sparta.springweb.dto;

import com.sparta.springweb.model.PostLike;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLikeUserDto {
    private Long userId;

    public PostLikeUserDto(PostLike postLike) {
        this.userId = postLike.getUser().getId();
    }
}
