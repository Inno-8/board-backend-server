package com.sparta.springweb.controller;

import com.sparta.springweb.dto.ReplyRequestDto;
import com.sparta.springweb.dto.ReplyResponseDto;
import com.sparta.springweb.global.common.response.ApiUtils;
import com.sparta.springweb.global.common.response.CommonResponse;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;
import com.sparta.springweb.model.Reply;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{commentId}")
    public CommonResponse<List<ReplyResponseDto>> getReply(@PathVariable Long commentId) {
        return ApiUtils.success(200, replyService.getReplyByCommentId(commentId));
    }

    @PostMapping("/{commentId}")
    public CommonResponse<Reply> createReply(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReplyRequestDto requestDto
    ) {
        if (userDetails != null) {
            String username = userDetails.getUser().getUsername();
            replyService.createReply(commentId, requestDto, username);
            return ApiUtils.success(201, null);
        }
        throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
    }

    @PutMapping("/{id}")
    public CommonResponse<Reply> updateReply(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReplyRequestDto replyRequestDto
    ) {
        if (userDetails != null) {
            String username = userDetails.getUser().getUsername();
            replyService.updateReply(id, replyRequestDto, username);
            return ApiUtils.success(201, null);
        }
        throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Reply> deleteReply(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (userDetails != null) {
            String username = userDetails.getUser().getUsername();
            replyService.deleteReply(id, username);
            return ApiUtils.success(200, null);
        }
        throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
    }
}