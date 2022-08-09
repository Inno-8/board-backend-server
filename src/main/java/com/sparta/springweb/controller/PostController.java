package com.sparta.springweb.controller;


import com.sparta.springweb.dto.PostRequestDto;
import com.sparta.springweb.dto.PostResponseDto;
import com.sparta.springweb.global.common.response.ApiUtils;
import com.sparta.springweb.global.common.response.CommonResponse;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // 게시글 조회
    @GetMapping()
    public CommonResponse<List<PostResponseDto>> post() {
        return ApiUtils.success(200, postService.posts());
    }

    // 게시글 디테일 조회
    @GetMapping("/{id}")
    public CommonResponse<PostResponseDto> post(@PathVariable Long id) {
        return ApiUtils.success(200, postService.getPostById(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<?> writePost(@RequestPart @Valid PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart(required = false) MultipartFile imageFile) {

        if (userDetails == null) {
            throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        String username = userDetails.getUser().getUsername();
        postService.writePost(postRequestDto, username, imageFile);
        return ApiUtils.success(201, null);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails == null) {
            throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        String username = userDetails.getUser().getUsername();
        postService.deletePost(id,username);
        return ApiUtils.success(200, null);
    }
}
