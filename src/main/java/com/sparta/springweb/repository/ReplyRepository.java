package com.sparta.springweb.repository;

import com.sparta.springweb.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByCommentId(long commentId);

    List<Reply> findAllByUsername(String username);
}
