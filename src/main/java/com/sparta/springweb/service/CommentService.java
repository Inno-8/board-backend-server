package com.sparta.springweb.service;

import com.sparta.springweb.dto.CommentRequestDto;
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
    public void createComment(Long postId, CommentRequestDto requestDto,String username) {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Comment comment = new Comment(postId, requestDto, username);
        commentRepository.save(comment);
    }

    // 댓글 수정
    @Transactional
    public String update(Long id, CommentRequestDto requestDto, String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다."));
        String writername = comment.getUsername();
        if (Objects.equals(writername, username)) {
            comment.update(requestDto);
            return "댓글 수정 완료";
        } return "작성한 유저가 아닙니다.";
    }

    // 댓글 삭제
    public String deleteComment(Long id, String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다."));
        String writername = comment.getUsername();
        if (Objects.equals(writername, username)) {
            commentRepository.deleteById(id);
            return "댓글 삭제 완료";
        }
        return "작성한 유저가 아닙니다.";
    }
}