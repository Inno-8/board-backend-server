package com.sparta.springweb.dto;

import com.sparta.springweb.model.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReplyResponseDto {
    private final Long id;
    private final String username;
    private final String content;

    private Long countReplyLike;

    @Builder
    public ReplyResponseDto(Reply reply, Long countReplyLike) {
        this.id = reply.getId();
        this.username = reply.getUsername();
        this.content = reply.getContent();
        this.countReplyLike = countReplyLike;
    }

}
