package com.example.board.post.exception;


import com.example.board.global.common.exception.GeneralException;

public class PostException extends GeneralException {
    public PostException(PostExceptionCode code) {
        super(code);
    }
}

