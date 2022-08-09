package com.sparta.springweb.util;

import com.sparta.springweb.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {

    private final PostService postService;

    @Scheduled(cron = "0 0 1 * * *")
    public void executeWithScheduled() {
        List<String> postTitleList = postService.deletePostsBlankComment();
        for (String postTitle : postTitleList) {
            log.info(String.format("게시물 <%s> 이 삭제되었습니다.", postTitle));
        }
    }
}

