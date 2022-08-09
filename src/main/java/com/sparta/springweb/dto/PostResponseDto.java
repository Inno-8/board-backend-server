package com.sparta.springweb.dto;

import com.sparta.springweb.model.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String name;
    private final String title;
    private final String content;
    private final String filePath;

    private final int countReply;

    @Builder
    public PostResponseDto(Post post, int countReply) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.name = post.getName();
        this.filePath = post.getFilePath();
        this.content = post.getContent();
        this.countReply = countReply;
    }
}
