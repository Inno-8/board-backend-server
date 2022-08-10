package com.sparta.springweb.model;

import com.sparta.springweb.dto.ReplyLikeRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "reply_like")
public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @Builder
    public ReplyLike(ReplyLikeRequestDto requestDto) {
        this.user = requestDto.getUser();
        this.reply = requestDto.getReply();
    }
}
