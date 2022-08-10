package com.sparta.springweb.dto;

import com.sparta.springweb.model.Reply;
import com.sparta.springweb.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReplyLikeRequestDto {
    private User user;
    private Reply reply;
}
