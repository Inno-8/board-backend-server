package com.sparta.springweb.service;

import com.sparta.springweb.dto.CommentRequestDto;
import com.sparta.springweb.global.error.exception.EntityNotFoundException;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;
import com.sparta.springweb.model.Comment;
import com.sparta.springweb.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 조회
    public List<Comment> getComment(Long postId) {
        return commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId);
    }

    // 댓글 작성
    @Transactional
    public void createComment(Long postId, CommentRequestDto requestDto, String username) {
        commentRepository.save(new Comment(postId, requestDto, username));
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
}
