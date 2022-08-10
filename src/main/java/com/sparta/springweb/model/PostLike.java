package com.sparta.springweb.model;

import com.sparta.springweb.dto.PostLikeRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "post_like")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostLike(PostLikeRequestDto requestDto) {
        this.user = requestDto.getUser();
        this.post = requestDto.getPost();
    }
}
