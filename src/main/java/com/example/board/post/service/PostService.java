package com.example.board.post.service;

import com.example.board.post.entity.Post;
import com.example.board.post.dto.PostRequestDTO;
import com.example.board.post.dto.PostResponseDTO;
import com.example.board.post.exception.PostException;
import com.example.board.post.exception.PostExceptionCode;
import com.example.board.post.repository.PostRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDTO.getPost createPost(PostRequestDTO.createPost postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .body(postRequest.getBody())
                .build();
        Post savedPost = postRepository.save(post);

        return PostResponseDTO.getPost.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .body(savedPost.getBody())
                .build();
    }

    public PostResponseDTO.getPosts getPosts() {
        return PostResponseDTO.getPosts.builder()
                .posts(postRepository.findAll().stream()
                        .map(post -> PostResponseDTO.getPost.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .body(post.getBody())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public PostResponseDTO.getPost updatePost(Long postId, PostRequestDTO.updatePost postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostExceptionCode.POST_NOT_FOUND));
        post.update(postRequest.getTitle(), postRequest.getBody());
        Post updatedPost = postRepository.save(post);

        return PostResponseDTO.getPost.builder()
                .id(updatedPost.getId())
                .title(updatedPost.getTitle())
                .body(updatedPost.getBody())
                .build();
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostExceptionCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }
}