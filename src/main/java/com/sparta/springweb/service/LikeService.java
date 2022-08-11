package com.sparta.springweb.service;

import com.sparta.springweb.dto.*;
import com.sparta.springweb.global.error.exception.EntityNotFoundException;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;
import com.sparta.springweb.model.*;
import com.sparta.springweb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;

    @Transactional
    public PostLikeResponseDto postLike(Long postId, Long uid) {
        User user = userRepository.findById(uid).orElseThrow(
                () -> new InvalidValueException(ErrorCode.NOT_AUTHORIZED)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOTFOUND_POST)
        );

        PostLike findPostLike = postLikeRepository.findByUserAndPost(user, post).orElse(null);

        if(findPostLike == null) {
            PostLikeRequestDto requestDto = new PostLikeRequestDto(user, post);
            PostLike postLike = new PostLike(requestDto);
            postLikeRepository.save(postLike);
        } else {
            postLikeRepository.deleteById(findPostLike.getId());
        }
        return new PostLikeResponseDto(postId, postLikeRepository.countByPost(post));
    }

    @Transactional
    public CommentLikeResponseDto commentLike(Long postId, Long commentId, Long uid) {
        User user = userRepository.findById(uid).orElseThrow(
                () -> new InvalidValueException(ErrorCode.NOT_AUTHORIZED)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOTFOUND_COMMENT)
        );

        CommentLike findCommentLike = commentLikeRepository.findByUserAndComment(user, comment).orElse(null);

        if(findCommentLike == null) {
            CommentLikeRequestDto requestDto = new CommentLikeRequestDto(user, comment);
            CommentLike commentLike = new CommentLike(requestDto);
            commentLikeRepository.save(commentLike);
        } else {
            commentLikeRepository.deleteById(findCommentLike.getId());
        }
        return new CommentLikeResponseDto(commentId, commentLikeRepository.countByComment(comment));
    }

    @Transactional
    public ReplyLikeResponseDto replyLike(Long postId, Long commentId, Long replyId, Long uid) {
        User user = userRepository.findById(uid).orElseThrow(
                () -> new InvalidValueException(ErrorCode.NOT_AUTHORIZED)
        );
        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOTFOUND_REPLY)
        );

        ReplyLike findReplyLike = replyLikeRepository.findByUserAndReply(user, reply).orElse(null);

        if(findReplyLike == null) {
            ReplyLikeRequestDto requestDto = new ReplyLikeRequestDto(user, reply);
            ReplyLike replyLike = new ReplyLike(requestDto);
            replyLikeRepository.save(replyLike);
        } else {
            replyLikeRepository.deleteById(findReplyLike.getId());
        }
        return new ReplyLikeResponseDto(replyId, replyLikeRepository.countByReply(reply));
    }

}
