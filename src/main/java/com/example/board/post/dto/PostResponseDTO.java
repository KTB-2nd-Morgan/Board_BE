package com.example.board.post.dto;

import java.util.List;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponseDTO {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class getPosts {
        List<getPost> posts;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class getPost {
        private Long id;
        private String title;
        private String body;
    }
}