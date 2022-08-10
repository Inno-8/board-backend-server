package com.sparta.springweb.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Entity
public class Reply extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String username;

    @Column
    private String content;

    @Setter
    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch  = FetchType.LAZY)
    private Comment comment;

    protected Reply() {}

    private Reply(String username, String content, Comment comment) {
        this.username = username;
        this.content = content;
        this.comment = comment;
    }

    public static Reply createReply(String username, String content, Comment comment) {
        return new Reply(username, content, comment);
    }

//    public void update(ReplyRequestDto replyRequestDto){
//        this.reply = replyRequestDto.getReply();
//    }
}
