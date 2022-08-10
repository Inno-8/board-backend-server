package com.sparta.springweb.service;

import com.sparta.springweb.dto.ReplyRequestDto;
import com.sparta.springweb.dto.ReplyResponseDto;
import com.sparta.springweb.global.error.exception.EntityNotFoundException;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;
import com.sparta.springweb.model.Comment;
import com.sparta.springweb.model.Reply;
import com.sparta.springweb.repository.CommentRepository;
import com.sparta.springweb.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    public final ReplyRepository replyRepository;
    public final CommentRepository commentRepository;

    public List<ReplyResponseDto> getReplyByCommentId(Long commentId) {
        return replyRepository.findAllByCommentId(commentId);
    }

    @Transactional
    public void createReply(Long commentId, ReplyRequestDto replyRequestDto, String username) {
        Reply reply = new Reply(replyRequestDto, username);
        Comment comment = existsComment(commentId);
        reply.setComment(comment);

        replyRepository.save(reply);
    }

    private Comment existsComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() ->new EntityNotFoundException(ErrorCode.NOTFOUND_COMMENT)
                );
    }

@Transactional
    public void updateReply(Long id, ReplyRequestDto replyRequestDto, String username) {
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() ->new EntityNotFoundException(ErrorCode.NOTFOUND_REPLY));
        if (!reply.getUsername().equals(username)){
            throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED);
        }
        reply.update(replyRequestDto);
    }

    public void deleteReply(Long id, String username) {
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() ->new EntityNotFoundException(ErrorCode.NOTFOUND_REPLY));
        if (!reply.getUsername().equals(username)){
            throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED);
        }
        replyRepository.deleteById(id);
    }
}
