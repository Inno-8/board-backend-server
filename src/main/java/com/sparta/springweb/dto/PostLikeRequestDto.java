package com.sparta.springweb.dto;

import com.sparta.springweb.model.Post;
import com.sparta.springweb.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PostLikeRequestDto {

    private User user;
    private Post post;
}
