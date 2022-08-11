package com.sparta.springweb.service;

import com.sparta.springweb.dto.*;
import com.sparta.springweb.global.error.exception.EntityNotFoundException;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;
import com.sparta.springweb.model.Comment;
import com.sparta.springweb.model.CommentLike;
import com.sparta.springweb.model.Post;
import com.sparta.springweb.model.Reply;
import com.sparta.springweb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;

    // 댓글 조회
    public List<CommentResponseDto> getComments(String username) {
        List<Comment> comments = commentRepository.findAllByUsername(username);
        List<CommentResponseDto> listcomments = new ArrayList<>();
        for (Comment comment : comments) {
            List<CommentLikeUserDto> commentLikeUserDtos = new ArrayList<>();
            Long countCommentLike = commentLikeRepository.countByComment(comment);
            List<CommentLike> commentLikes = commentLikeRepository.findAllByComment(comment);
            for (CommentLike commentLike : commentLikes) {
                CommentLikeUserDto commentLikeUserDto = new CommentLikeUserDto(commentLike);
                commentLikeUserDtos.add(commentLikeUserDto);
            }
            CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                    .comment(comment)
                    .countCommentLike(countCommentLike)
                    .build();
            listcomments.add(commentResponseDto);
        }
        return listcomments;
    }

    // 댓글 상세 조회
    public CommentDetailResponseDto viewCommentDetail(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOTFOUND_COMMENT));

        List<CommentLikeUserDto> commentLikeUserDtos = new ArrayList<>();
        List<CommentLike> commentLikes = commentLikeRepository.findAllByCommentId(id);
        for (CommentLike commentLike : commentLikes) {
            CommentLikeUserDto commentLikeUserDto = new CommentLikeUserDto(commentLike);
            commentLikeUserDtos.add(commentLikeUserDto);
        }
        List<Reply> replyList = replyRepository.findAllByCommentId(id);
        List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();
        for (Reply reply : replyList) {
            Long countReplyLike = replyLikeRepository.countByReply(reply);
            replyResponseDtoList.add(new ReplyResponseDto(reply, countReplyLike));
        }
        Long countCommentLike = commentLikeRepository.countByComment(comment);
        return new CommentDetailResponseDto(comment, countCommentLike, replyResponseDtoList);
    }

    // 댓글 작성
    @Transactional
    public void createComment(Long postId, CommentRequestDto requestDto, String username) {

        Comment comment = new Comment(requestDto, username);
        Post post = existsPost(postId);
        comment.setPost(post);

        commentRepository.save(comment);
    }

    // 댓글 수정
    @Transactional
    public void update(Long id, CommentRequestDto requestDto, String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOTFOUND_COMMENT));
        if (!Objects.equals(comment.getUsername(), username)) {
            throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED);
        }
        comment.update(requestDto);
    }

    // 댓글 삭제
    public void deleteComment(Long id, String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOTFOUND_COMMENT));
        String writername = comment.getUsername();
        if (!Objects.equals(writername, username)) {
            throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED);
        }
        commentRepository.deleteById(id);
    }

    private Post existsPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOTFOUND_POST));
    }
}
