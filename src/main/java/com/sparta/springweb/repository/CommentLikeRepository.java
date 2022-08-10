package com.sparta.springweb.repository;

import com.sparta.springweb.model.Comment;
import com.sparta.springweb.model.CommentLike;
import com.sparta.springweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
    Long countByComment(Comment comment);
    List<CommentLike> findAllByComment(Comment comment);

    List<CommentLike> findAllByCommentId(Long id);
}
