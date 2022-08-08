package com.sparta.springweb.controller;


import com.sparta.springweb.global.common.response.ApiUtils;
import com.sparta.springweb.global.common.response.CommonResponse;
import com.sparta.springweb.model.Reply;
import com.sparta.springweb.service.ReplyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }


    //댓글id의 대댓글 목록 조회
    @GetMapping("/{commentId}")
    public CommonResponse<List<Reply>> getReply(@PathVariable Long commentId) {
        return ApiUtils.success(200, replyService.getReply(commentId));
    }
}

//
//    //대댓글 작성
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/{commentId}")
//    public CommonResponse<?> createReply(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (userDetails != null) {
//            String username = userDetails.getUser().getUsername();
//            replyService.createReply(commentId, requestDto, username);
//            return ApiUtils.success(201, null);
//        }
//        throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
//    }
//
//
//    //대댓글 수정
//    @PutMapping("/{id}")
//    public CommonResponse<?> updateReply(
//            @PathVariable Long id,
//            @RequestBody ReplyRequestDto requestDto,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ) {
//        if (userDetails != null) {
//            String username = userDetails.getUser().getUsername();
//            replyService.updateReply(id, requestDto, username);
//            return ApiUtils.success(200, null);
//        }
//        throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
//    }
//
//
//    //대댓글 삭제
//    @DeleteMapping("/{id}")
//    public CommonResponse<?> deleteReply(
//            @PathVariable Long id,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ) {
//        if (userDetails != null) {
//            String username = userDetails.getUser().getUsername();
//            replyService.deleteReply(id, username);
//            return ApiUtils.success(200, null);
//        }
//        throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
//    }
//}
