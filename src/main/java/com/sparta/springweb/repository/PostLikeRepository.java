package com.sparta.springweb.repository;

import com.sparta.springweb.model.Post;
import com.sparta.springweb.model.PostLike;
import com.sparta.springweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);

    Long countByPost(Post post);

    List<PostLike> findAllByPost(Post post);

    List<PostLike> findAllByPostId(Long id);
}
