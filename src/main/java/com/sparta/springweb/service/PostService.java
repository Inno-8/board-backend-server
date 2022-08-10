package com.sparta.springweb.service;

import com.sparta.springweb.dto.*;
import com.sparta.springweb.global.error.exception.EntityNotFoundException;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;
import com.sparta.springweb.model.Comment;
import com.sparta.springweb.model.Post;
import com.sparta.springweb.model.PostLike;
import com.sparta.springweb.repository.CommentLikeRepository;
import com.sparta.springweb.repository.CommentRepository;
import com.sparta.springweb.repository.PostLikeRepository;
import com.sparta.springweb.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final StorageService storageService;

    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public void writePost(PostRequestDto postRequestDto, String username, MultipartFile imageFile) {
        String filePath = "";
        if (imageFile != null) {
            filePath = storageService.uploadFile(imageFile);
        }
        postRepository.save(Post.createPost(username, postRequestDto.getTitle(), postRequestDto.getContents(), filePath));
    }

    // 게시글 조회
    public List<PostResponseDto> posts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> listContents = new ArrayList<>();
        for (Post post : posts) {
            // + 댓글 개수 카운팅 (추가 기능)
            int countReply = commentRepository.countByPostId(post.getId());
            // + 좋아요 개수 카운팅
            List<PostLikeUserDto> postLikeUserDtos = new ArrayList<>();
            Long countPostLike = postLikeRepository.countByPost(post);
            List<PostLike> postLikes = postLikeRepository.findAllByPost(post);
            for (PostLike postLike : postLikes)
            {
                PostLikeUserDto postLikeUserDto = new PostLikeUserDto(postLike);
                postLikeUserDtos.add(postLikeUserDto);
            }
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .post(post)
                    .countReply(countReply)
                    .countPostLike(countPostLike)
                    .build();
            listContents.add(postResponseDto);
        }
        return listContents;
    }

    // 게시글 상세 조회
    public PostDetailResponseDto viewPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOTFOUND_POST));

        List<PostLikeUserDto> postLikeUserDtos = new ArrayList<>();
        List<PostLike> postLikes = postLikeRepository.findAllByPostId(id);
        for (PostLike postLike : postLikes)
        {
            PostLikeUserDto postLikeUserDto = new PostLikeUserDto(postLike);
            postLikeUserDtos.add(postLikeUserDto);
        }
        List<Comment> commentList = commentRepository.findAllByPostIdOrderByCreatedAtDesc(id);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            Long countCommentLike = commentLikeRepository.countByComment(comment);
            commentResponseDtoList.add(new CommentResponseDto(comment, countCommentLike));
        }
        Long countPostLike = postLikeRepository.countByPost(post);

        return new PostDetailResponseDto(post, countPostLike, commentResponseDtoList);
    }

    @Transactional
    public void updatePost(Long id, PostUpdateRequestDto dto, MultipartFile imageFile) {
        Post existingPost = exists(id);
        String filePath = "";

        if (imageFile != null) {
            filePath = storageService.uploadFile(imageFile);
        }

        existingPost.updatePost(dto.getTitle(), dto.getContent(), filePath);

        postRepository.save(existingPost);
    }

    // 게시글 삭제
    public void deletePost(Long id, String userName) {

        if (!(userName.equals(exists(id).getName()))) {
            throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED);
        }
        postRepository.deleteById(id);
    }

    private Post exists(long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.NOTFOUND_POST));
    }

    public PostResponseDto getPostById(Long id) {
        Post post = exists(id);
        int numberOfComments = post.getComments().size();
        Long numberOfLikes = Long.valueOf(post.getPostLikeList().size());
        return new PostResponseDto(post, numberOfComments, numberOfLikes);
    }

    @Transactional
    public List<String> deletePostsBlankComment() {
        List<Post> posts = postRepository.findAll();
        ArrayList<String> postTitleList = new ArrayList<>();
        for (Post post : posts) {
            if (post.getComments().size() == 0) {
                postRepository.deleteById(post.getId());
                postTitleList.add(post.getTitle());
            }
        }
        return postTitleList;
    }

    public List<PostResponseDto> getPosts(String username) {
        List<Post> posts = postRepository.findAllByName(username);
        List<PostResponseDto> listContents = new ArrayList<>();
        for (Post post : posts) {
            // + 댓글 개수 카운팅 (추가 기능)
            int countReply = commentRepository.countByPostId(post.getId());
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .post(post)
                    .countReply(countReply)
                    .build();
            listContents.add(postResponseDto);
        }
        return listContents;
    }
}
