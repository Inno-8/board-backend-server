package com.sparta.springweb.service;

import com.sparta.springweb.dto.ReplyLikeUserDto;
import com.sparta.springweb.dto.ReplyRequestDto;
import com.sparta.springweb.dto.ReplyResponseDto;
import com.sparta.springweb.global.error.exception.EntityNotFoundException;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.model.Comment;
import com.sparta.springweb.model.Reply;
import com.sparta.springweb.model.ReplyLike;
import com.sparta.springweb.repository.CommentRepository;
import com.sparta.springweb.repository.ReplyLikeRepository;
import com.sparta.springweb.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    public final ReplyRepository replyRepository;
    public final CommentRepository commentRepository;

    public final ReplyLikeRepository replyLikeRepository;

    // 대댓글 조회
    public List<ReplyResponseDto> getReplyByCommentId(Long commentId) {
        List<Reply> replies = replyRepository.findAllByCommentId(commentId);
        List<ReplyResponseDto> listreplies = new ArrayList<>();
        for (Reply reply : replies) {
            List<ReplyLikeUserDto> replyLikeUserDtos = new ArrayList<>();
            Long countReplyLike = replyLikeRepository.countByReply(reply);
            List<ReplyLike> replyLikes = replyLikeRepository.findAllByReply(reply);
            for (ReplyLike replyLike : replyLikes) {
                ReplyLikeUserDto replyLikeUserDto = new ReplyLikeUserDto(replyLike);
                replyLikeUserDtos.add(replyLikeUserDto);
            }
            ReplyResponseDto replyResponseDto = ReplyResponseDto.builder()
                    .reply(reply)
                    .countReplyLike(countReplyLike)
                    .build();
            listreplies.add(replyResponseDto);
        }
        return listreplies;
//        return replies.stream().map(m -> new ReplyResponseDto(m.getId(),m.getUsername(), m.getContent())).collect(Collectors.toList());
    }

    @Transactional
    public void createReply(Long commentId, ReplyRequestDto replyRequestDto, String username) {
        Comment comment = existsComment(commentId);
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
