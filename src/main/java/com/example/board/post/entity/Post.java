package com.example.board.post.entity;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    public void update(String title, String body) {
        if (Objects.nonNull(title)) {
            this.title = title;
        }
        if (Objects.nonNull(body)) {
            this.body = body;
        }
    }
}