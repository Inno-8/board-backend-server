package com.sparta.springweb.model;

import com.sparta.springweb.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Reply extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String reply;


    //@OneToMany 대댓글


    public Reply(Long postId, ReplyRequestDto requestDto, String username) {
        this.reply = requestDto.getReply();
        this.username = username;
        this.postId = postId;
    }

    public void update(ReplyRequestDto requestDto) {
        this.reply = requestDto.getReply();
    }
}

