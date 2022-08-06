package com.sparta.springweb.service;

import com.sparta.springweb.dto.ContentsRequestDto;
import com.sparta.springweb.dto.ContentsResponseDto;
import com.sparta.springweb.model.Contents;
import com.sparta.springweb.repository.CommentRepository;
import com.sparta.springweb.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ContentsService {

    private final ContentsRepository ContentsRepository;
    private final CommentRepository commentRepository;
    private final StorageService storageService;

    @Transactional
    public Contents createContents(ContentsRequestDto contentsRequestDto, String username, MultipartFile imageFile) {
        String filePath = storageService.uploadFile(imageFile);
        return ContentsRepository.save(new Contents(contentsRequestDto, username, filePath));
    }

    // 게시글 조회
    public List<ContentsResponseDto> getContents() {
        List<Contents> contents = ContentsRepository.findAllByOrderByCreatedAtDesc();
        List<ContentsResponseDto> listContents = new ArrayList<>();
        for (Contents content : contents) {
            // + 댓글 개수 카운팅 (추가 기능)
            int countReply = commentRepository.countByPostId(content.getId());
            ContentsResponseDto contentsResponseDto = ContentsResponseDto.builder()
                    .content(content)
                    .countReply(countReply)
                    .build();
            listContents.add(contentsResponseDto);
        }
        return listContents;
    }

    // 게시글 수정 기능 (사용 안함)
    @Transactional
    public Long update(Long id, ContentsRequestDto requestDto) {
        Contents Contents = ContentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        Contents.update(requestDto);
        return Contents.getId();
    }

    // 게시글 삭제
    public void deleteContent(Long ContentId, String userName) {
        String writer = ContentsRepository.findById(ContentId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")).getName();
        if (Objects.equals(writer, userName)) {
            ContentsRepository.deleteById(ContentId);
        }else new IllegalArgumentException("작성한 유저가 아닙니다.");
    }
}