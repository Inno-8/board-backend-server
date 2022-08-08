package com.sparta.springweb.service;

import com.sparta.springweb.dto.CommentRequestDto;
import com.sparta.springweb.dto.ReplyRequestDto;
import com.sparta.springweb.model.Reply;
import com.sparta.springweb.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public List<Reply> getReply(Long commentId) {
        return replyRepository.findAllByOrderByCreatedAtDesc(commentId);
    }

    public void createReply(Long commentId, CommentRequestDto requestDto, String username) {
    }

    public void updateReply(Long id, ReplyRequestDto requestDto, String username) {
    }

    public void deleteReply(Long id, String username) {
    }

}
