package com.sparta.springweb.controller;

import com.sparta.springweb.dto.CommentRequestDto;
import com.sparta.springweb.dto.CommentResponseDto;
import com.sparta.springweb.global.common.response.ApiUtils;
import com.sparta.springweb.global.common.response.CommonResponse;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    // 게시글 id 로 댓글 조회
    @GetMapping("/{postId}")
    public CommonResponse<List<CommentResponseDto>> getComment(@PathVariable Long postId) {
        return ApiUtils.success(200, commentService.getCommentByPostId(postId));
    }

    // 댓글 작성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{postId}")
    public CommonResponse<?> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            String username = userDetails.getUser().getUsername();
            commentService.createComment(postId, requestDto, username);
            return ApiUtils.success(201, null);
        }
        throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public CommonResponse<?>  updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            String username = userDetails.getUser().getUsername();
            commentService.update(id, requestDto, username);
            return ApiUtils.success(200, null);
        }
        throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public CommonResponse<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID
        if (userDetails != null) {
            String username = userDetails.getUser().getUsername();
            commentService.deleteComment(id, username);
            return ApiUtils.success(200, null);
        }
        throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
    }
}
