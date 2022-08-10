package com.sparta.springweb.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplyResponseDto {

    private final String username;
    private final String content;

    @Builder
    public ReplyResponseDto(String username, String content) {
        this.username = username;
        this.content = content;
    }
}
