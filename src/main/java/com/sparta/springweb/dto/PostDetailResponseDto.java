package com.sparta.springweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.springweb.model.Post;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
public class PostDetailResponseDto {
    private final Long id;

    private final String name;

    private final String title;

    private final String content;

    private final String filePath;

    private Long countPostLike;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;

    private List<CommentResponseDto> commentList;


    public PostDetailResponseDto(Post post,
                                 Long countPostLike,
                                 List<CommentResponseDto> commentList) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.name = post.getName();
        this.filePath = post.getFilePath();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.countPostLike = countPostLike;
        this.commentList = commentList;
    }
}
