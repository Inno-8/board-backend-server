package com.sparta.springweb.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplyResponseDto {
    private final Long id;
    private final String username;
    private final String content;

    @Builder
    public ReplyResponseDto(Long id,String username, String content) {
        this.id = id;
        this.username = username;
        this.content = content;
    }
}
