package com.sparta.springweb.dto;

import com.sparta.springweb.model.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String name;
    private final String content;

    @Builder
    public CommentResponseDto(Long id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public static CommentResponseDto createDTO(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getUsername(), comment.getComment());
    }
}
