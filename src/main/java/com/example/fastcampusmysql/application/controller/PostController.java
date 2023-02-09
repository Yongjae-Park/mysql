package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    final private PostWriteService postWriteService;

    @PostMapping("")
    public Long register(PostDto postDto) {
        return postWriteService.create(postDto);
    }
}
