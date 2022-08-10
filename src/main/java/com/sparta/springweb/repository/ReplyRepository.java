package com.sparta.springweb.repository;

import com.sparta.springweb.dto.ReplyResponseDto;
import com.sparta.springweb.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {


    List<ReplyResponseDto> findAllByCommentId(Long commentId);
}
