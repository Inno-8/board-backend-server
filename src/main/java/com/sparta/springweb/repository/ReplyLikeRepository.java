package com.sparta.springweb.repository;

import com.sparta.springweb.model.Reply;
import com.sparta.springweb.model.ReplyLike;
import com.sparta.springweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
    Optional<ReplyLike> findByUserAndReply(User user, Reply reply);

    Long countByReply(Reply reply);

    List<ReplyLike> findAllByReply(Reply reply);
}
