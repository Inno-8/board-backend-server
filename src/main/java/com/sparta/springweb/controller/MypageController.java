package com.sparta.springweb.controller;

import com.sparta.springweb.dto.PostResponseDto;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MypageController {
    private final PostService postService;

    @GetMapping("/api/mypage/post")
    public List<PostResponseDto> getUserPost (@AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getPosts(userDetails.getUser().getUsername());

    }
}
