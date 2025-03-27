package com.example.board.post.controller;

import com.example.board.global.response.CommonResponse;
import com.example.board.post.dto.PostRequestDTO;
import com.example.board.post.dto.PostResponseDTO;
import com.example.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public CommonResponse<PostResponseDTO.getPost> createPost(
            @RequestBody PostRequestDTO.createPost postRequest
    ) {
        return CommonResponse.onSuccess(postService.createPost(postRequest));
    }

    @GetMapping
    public CommonResponse<PostResponseDTO.getPosts> getPosts() {
        return CommonResponse.onSuccess(postService.getPosts());
    }

    @PutMapping("/{postId}")
    public CommonResponse<PostResponseDTO.getPost> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDTO.updatePost postRequest
    ) {
        return CommonResponse.onSuccess(postService.updatePost(postId, postRequest));
    }

    @DeleteMapping("/{postId}")
    public CommonResponse<?> deletePost(
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);
        return CommonResponse.onNoContent();
    }
}
