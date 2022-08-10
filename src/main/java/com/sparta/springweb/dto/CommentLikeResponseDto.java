package com.sparta.springweb.dto;

import lombok.Getter;

@Getter
public class CommentLikeResponseDto {
    private Long commentId;
    private Long likeCount;

    public CommentLikeResponseDto(Long commentId, Long likeCount) {
        this.commentId = commentId;
        this.likeCount = likeCount;
    }
}
