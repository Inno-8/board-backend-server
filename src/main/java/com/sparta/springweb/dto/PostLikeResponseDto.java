package com.sparta.springweb.dto;

import lombok.Getter;

@Getter
public class PostLikeResponseDto {
    private Long postId;
    private Long likeCount;

    public PostLikeResponseDto(Long postId, Long likeCount) {
        this.postId = postId;
        this.likeCount = likeCount;
    }
}
