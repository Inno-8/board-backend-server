package com.sparta.springweb.dto;

import com.sparta.springweb.model.Comment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class CommentDetailResponseDto {

    private final String name;
    private final String content;

    private Long countCommentLike;

    private List<ReplyResponseDto> replyList;

    public CommentDetailResponseDto(Comment comment,
                                    Long countCommentLike,
                                    List<ReplyResponseDto> replyList) {
        this.name = comment.getUsername();
        this.content = comment.getComment();
        this.countCommentLike = countCommentLike;
        this.replyList = replyList;

    }
}
