package com.sparta.springweb.service;

import com.sparta.springweb.dto.PostRequestDto;
import com.sparta.springweb.dto.PostResponseDto;
import com.sparta.springweb.global.error.exception.EntityNotFoundException;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;
import com.sparta.springweb.model.Post;
import com.sparta.springweb.repository.CommentRepository;
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

    @Transactional
    public void writePost(PostRequestDto postRequestDto, String username, MultipartFile imageFile) {
        String filePath = "";
        if (imageFile != null) {
            filePath = storageService.uploadFile(imageFile);
        }
        postRepository.save(new Post(postRequestDto, username, filePath));
    }

    // 게시글 조회
    public List<PostResponseDto> posts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
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

    // 게시글 수정 기능 (사용 안함)
    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        Post Post = postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOTFOUND_POST)
        );
        Post.update(requestDto);
        return Post.getId();
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
        return new PostResponseDto(post, numberOfComments);
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
}
