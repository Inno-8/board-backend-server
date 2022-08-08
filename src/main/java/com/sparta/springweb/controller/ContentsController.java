package com.sparta.springweb.controller;


import com.sparta.springweb.dto.ContentsRequestDto;
import com.sparta.springweb.dto.ContentsResponseDto;
import com.sparta.springweb.global.common.response.ApiUtils;
import com.sparta.springweb.global.common.response.CommonResponse;
import com.sparta.springweb.global.error.exception.EntityNotFoundException;
import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;
import com.sparta.springweb.model.Contents;
import com.sparta.springweb.repository.ContentsRepository;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.ContentsService;
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
public class ContentsController {

    private final ContentsRepository ContentsRepository;
    private final ContentsService ContentsService;

    // 게시글 조회
    @GetMapping("/api/contents")
    public CommonResponse<List<ContentsResponseDto>> getContents() {
        return ApiUtils.success(200, ContentsService.getContents());
    }

    // 게시글 디테일 조회
    @GetMapping("/api/contents/{id}")
    public CommonResponse<Contents> getContents(@PathVariable Long id) {
        Contents contents = ContentsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOTFOUND_POST));
        return ApiUtils.success(200, contents);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/contents", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<?> createContents(@RequestPart @Valid ContentsRequestDto contentsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart(required = false) MultipartFile imageFile) {

        if (userDetails == null) {
            throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        String username = userDetails.getUser().getUsername();
        ContentsService.createContents(contentsRequestDto, username, imageFile);
        return ApiUtils.success(201, null);
    }
}
