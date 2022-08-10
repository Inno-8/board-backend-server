package com.sparta.springweb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.springweb.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String comment;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"comment"})
    private List<CommentLike> commentLikeList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comment", orphanRemoval = true)
    private final List<Reply> replies = new ArrayList<>();


    public Comment(CommentRequestDto requestDto, String username) {
        this.comment = requestDto.getComment();
        this.username = username;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}

