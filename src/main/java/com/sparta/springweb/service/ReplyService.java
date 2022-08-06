package com.sparta.springweb.service;

import com.sparta.springweb.model.dto.ReplyRequestDto;
import com.sparta.springweb.model.Reply;
import com.sparta.springweb.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository ReplyRepository;

    // 댓글 조회
    public List<Reply> getReply(Long postId) {
        return ReplyRepository.findAllByPostIdOrderByCreatedAtDesc(postId);
    }

    // 댓글 작성
    @Transactional
    public void createReply(Long postId, ReplyRequestDto requestDto,String username) {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Reply reply = new Reply(postId, requestDto, username);
        ReplyRepository.save(reply);
    }

    // 댓글 수정
    @Transactional
    public String update(Long id, ReplyRequestDto requestDto, String username) {
        Reply reply = ReplyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다."));
        String writername = reply.getUsername();
        if (Objects.equals(writername, username)) {
            reply.update(requestDto);
            return "댓글 수정 완료";
        } return "작성한 유저가 아닙니다.";
    }

    // 댓글 삭제
    public String deleteReply(Long id, String username) {
        Reply reply = ReplyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다."));
        String writername = reply.getUsername();
        if (Objects.equals(writername, username)) {
            ReplyRepository.deleteById(id);
            return "댓글 삭제 완료";
        }
        return "작성한 유저가 아닙니다.";
    }
}