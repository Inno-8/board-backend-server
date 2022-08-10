package com.sparta.springweb.dto;

import com.sparta.springweb.model.Reply;
import lombok.Getter;

@Getter
public class ReplyResponseDto {

    private final Long id;
    private final String username;
    private final String reply;

    public ReplyResponseDto(Reply reply){
        this.id = reply.getId();
        this.username = reply.getUsername();
        this.reply = reply.getReply();
    }
}
