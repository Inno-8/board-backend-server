package com.sparta.springweb.dto;

import com.sparta.springweb.model.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentResponseDto {
    private final Long id;
    private final String name;
    private final String content;

    private Long countCommentLike;

    @Builder
    public CommentResponseDto(Comment comment, Long countCommentLike) {
        this.id = comment.getId();
        this.name = comment.getUsername();
        this.content = comment.getComment();
        this.countCommentLike = countCommentLike;
    }

//    public static CommentResponseDto createDTO(Comment comment) {
//        return new CommentResponseDto(comment.getId(), comment.getUsername(), comment.getComment());
//    }
}
