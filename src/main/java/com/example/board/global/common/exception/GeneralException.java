package com.example.board.global.common.exception;

import com.example.board.global.response.code.BaseCode;
import com.example.board.global.response.code.Reason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
    private BaseCode code;

    public Reason getErrorReason() {
        return this.code.getReason();
    }

    public Reason getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
