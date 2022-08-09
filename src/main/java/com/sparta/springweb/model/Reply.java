package com.sparta.springweb.model;


import com.sparta.springweb.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Reply {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String username;

    @Column
    private String reply;

    @Setter
    @JoinColumn(name = "comment_Id", nullable = false)
    @ManyToOne(fetch  = FetchType.LAZY)
    private Comment comment;

//    @JoinColumn(name = "comment", insertable = false, updatable = false)
//    @ManyToOne(targetEntity = Comment.class, fetch  = FetchType.LAZY)
//    private Comment comment;
//
//    @Column(name = "comment_Id")
//    private Long commentId;
//
//    public void setComment(Comment comment){
//        setCommentId(comment.getId());
//        this.comment = comment;
//    }


    public Reply(ReplyRequestDto replyRequestDto, String username) {
        this.reply = replyRequestDto.getReply();
        this.username = username;
    }

    public void update(ReplyRequestDto replyRequestDto){
        this.reply = replyRequestDto.getReply();
    }
}
