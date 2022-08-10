package com.sparta.springweb.dto;

import com.sparta.springweb.model.Comment;
import com.sparta.springweb.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CommentLikeRequestDto {
    private User user;
    private Comment comment;
}
