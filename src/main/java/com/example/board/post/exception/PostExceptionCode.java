package com.example.board.post.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.example.board.global.response.code.BaseCode;
import com.example.board.global.response.code.Reason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostExceptionCode implements BaseCode {

    // Post + 404
    POST_NOT_FOUND(NOT_FOUND, "POST404_1", "게시글을 찾을 수 없습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public Reason getReason() {
        return Reason.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public Reason getReasonHttpStatus() {
        return Reason.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}