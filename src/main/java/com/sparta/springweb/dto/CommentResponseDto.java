package com.sparta.springweb.dto;

import com.sparta.springweb.model.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final String name;
    private final String content;

    @Builder
    public CommentResponseDto(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public static CommentResponseDto creatDTO(Comment comment) {
        return new CommentResponseDto(comment.getUsername(), comment.getComment());
    }
}
