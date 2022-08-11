package com.sparta.springweb.controller;

import com.sparta.springweb.dto.CommentResponseDto;
import com.sparta.springweb.dto.PostResponseDto;
import com.sparta.springweb.dto.ReplyResponseDto;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.CommentService;
import com.sparta.springweb.service.PostService;
import com.sparta.springweb.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypage")
public class MypageController {
    private final PostService postService;
    private final CommentService commentService;
    private final ReplyService replyService;
    @GetMapping("/myposts")
    public List<PostResponseDto> getUserPost (@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPosts(userDetails.getUser().getUsername());
    }

    @GetMapping("/mycomments")
    public List<CommentResponseDto> getUserComments (@AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.getComments(userDetails.getUser().getUsername());
    }

    @GetMapping("/myreplies")
    public List<ReplyResponseDto> getUserReplies (@AuthenticationPrincipal UserDetailsImpl userDetails){
        return replyService.getReplies(userDetails.getUser().getUsername());
    }

//    @GetMapping("/likedposts")
//    public List<PostResponseDto> getLikedPosts (@AuthenticationPrincipal UserDetailsImpl userDetails){
//            return postService.getLikedPosts(userDetails.getUser().getUsername());
//        }

//    @GetMapping("/likedcomments")
//    public List<PostResponseDto> getLikedComments (@AuthenticationPrincipal UserDetailsImpl userDetails){
//        return postService.getLikedPosts(userDetails.getUser().getUsername());
//    }
    }
