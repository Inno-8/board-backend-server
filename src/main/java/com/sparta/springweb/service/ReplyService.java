package com.sparta.springweb.service;

import com.sparta.springweb.dto.ReplyRequestDto;
import com.sparta.springweb.dto.ReplyResponseDto;
import com.sparta.springweb.global.error.exception.EntityNotFoundException;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.model.Comment;
import com.sparta.springweb.model.Reply;
import com.sparta.springweb.repository.CommentRepository;
import com.sparta.springweb.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {
    public final ReplyRepository replyRepository;
    public final CommentRepository commentRepository;

    public List<ReplyResponseDto> getReplyByCommentId(Long commentId) {
        List<Reply> replies = replyRepository.findAllByCommentId(commentId);
        return replies.stream().map(m -> new ReplyResponseDto(m.getUsername(), m.getContent())).collect(Collectors.toList());
    }

    @Transactional
    public void createReply(Long commentId, ReplyRequestDto replyRequestDto, String username) {
        Comment comment = existsComment(commentId);
        Reply reply = Reply.createReply(username, replyRequestDto.getContent(), comment);
        comment.newReply(reply);

        replyRepository.save(Reply.createReply(username, replyRequestDto.getContent(), comment));
    }


//@Transactional
//    public void updateReply(Long id, ReplyRequestDto replyRequestDto, String username) {
//        ReplyResponseDto reply = repliesRepository.findById(id)
//                .orElseThrow(() ->new EntityNotFoundException(ErrorCode.NOTFOUND_REPLY));
//        if (!reply.getUsername().equals(username)){
//            throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED);
//        }
//        reply.update(replyRequestDto);
//    }
//
//    public void deleteReply(Long id, String username) {
//        ReplyResponseDto reply = repliesRepository.findById(id)
//                .orElseThrow(() ->new EntityNotFoundException(ErrorCode.NOTFOUND_REPLY));
//        if (!reply.getUsername().equals(username)){
//            throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED);
//        }
//        repliesRepository.deleteById(id);
//    }

    private Comment existsComment(long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.NOTFOUND_COMMENT));
    }
}
