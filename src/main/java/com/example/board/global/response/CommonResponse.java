package com.example.board.global.response;

import com.example.board.global.response.code.BaseCode;
import com.example.board.global.response.code.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@JsonPropertyOrder({"isSuccess", "code", "message", "data"})
public record CommonResponse<T>(
        @JsonProperty("isSuccess") Boolean isSuccess,
        @JsonProperty("code") String code,
        @JsonProperty("message") String message,
        @JsonProperty("data") T data
) {

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> CommonResponse<T> onSuccess(T data) {
        return new CommonResponse<>(true, "200", SuccessStatus.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResponse<T> of(BaseCode code, T data) {
        return new CommonResponse<>(true, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), data);
    }

    public static <T> CommonResponse<T> onNoContent() {
        return new CommonResponse<>(
                true,
                SuccessStatus.NO_CONTENT.getCode(),
                SuccessStatus.NO_CONTENT.getMessage(),
                null
        );
    }

    public static <T> CommonResponse<T> onFailure(String code, String message, T data) {
        return new CommonResponse<>(false, code, message, data);
    }

    public static <T> CommonResponse<T> onFailure(BaseCode code, T data) {
        return new CommonResponse<>(false, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), data);
    }
}