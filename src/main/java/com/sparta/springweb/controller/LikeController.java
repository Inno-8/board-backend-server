package com.sparta.springweb.controller;

import com.sparta.springweb.global.common.response.ApiUtils;
import com.sparta.springweb.global.common.response.CommonResponse;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{postId}/likes")
    public CommonResponse<?> PostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        likeService.postLike(postId, userDetails.getUser().getId());
        return ApiUtils.success(200, null);
    }

    @PostMapping("/{postId}/{commentId}/likes")
    public CommonResponse<?> CommentLike(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        likeService.commentLike(postId, commentId, userDetails.getUser().getId());
        return ApiUtils.success(200, null);
    }

    @PostMapping("/{postId}/{commentId}/{replyId}/likes")
    public CommonResponse<?> replyLike(@PathVariable Long postId, @PathVariable Long commentId, @PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        likeService.replyLike(postId, commentId, replyId, userDetails.getUser().getId());
        return ApiUtils.success(200, null);
    }
}
