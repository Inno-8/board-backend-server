package com.sparta.springweb.model;


import com.sparta.springweb.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Reply extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Column(nullable = false)
    private String reply;

    @Column(nullable = false)
    private String username;


    @Column(nullable = false)
    private Long commentId;
//    @ManyToOne
//    @JoinColumn(name = "commentId", nullable = false)
//    private Comment comment;


    public Reply (ReplyRequestDto requestDto, String username, Long commentId){
        this.reply = requestDto.getReply();
        this.username = username;
        this.commentId = commentId;
    }




}
