package com.example.board;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    // Health check
    @GetMapping("/")
    public String health() {
        return "I'm healthy!";
    }
}
