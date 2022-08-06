package com.sparta.springweb.controller;

import com.sparta.springweb.dto.CommentRequestDto;
import com.sparta.springweb.model.Comment;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reply")
public class CommentController {
    private final CommentService commentService;

    // 게시글 id 로 댓글 조회
    @GetMapping("/{postId}")
    public List<Comment> getComment(@PathVariable Long postId) {
        return commentService.getComment(postId);
    }

    // 댓글 작성
    @PostMapping("/{postId}")
    public String createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID
        if (userDetails != null) {
            String username = userDetails.getUser().getUsername();
            commentService.createComment(postId, requestDto, username);
            return "댓글 작성 완료";
        }
        return "로그인이 필요한 기능입니다.";
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public String updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID
        if (userDetails != null) {
            String username = userDetails.getUser().getUsername();
            String result = commentService.update(id, requestDto, username);
            return result;
        }
        return "로그인이 필요한 기능입니다.";
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID
        if (userDetails != null) {
            String username = userDetails.getUser().getUsername();
            String result = commentService.deleteComment(id, username);
            return result;
        }
        return "로그인이 필요한 기능입니다.";
    }
}