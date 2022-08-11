package com.sparta.springweb.dto;

import lombok.Getter;

@Getter
public class ReplyLikeResponseDto {
    private Long replyId;
    private Long likeCount;

    public ReplyLikeResponseDto(Long replyId, Long likeCount) {
        this.replyId = replyId;
        this.likeCount = likeCount;
    }
}
