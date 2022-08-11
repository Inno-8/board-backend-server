package com.sparta.springweb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"reply"})
    private List<ReplyLike> ReplyLikeList = new ArrayList<>();

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
