package com.example.board.global.response;

import com.example.board.global.response.code.BaseCode;
import com.example.board.global.response.code.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "data"})
public class CommonResponse<T> {



    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Java 8 날짜/시간 모듈 등록
            mapper.registerModule(new JavaTimeModule());
            // 날짜와 시간을 ISO-8601 형식의 문자열로 직렬화
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            // 이쁘게 출력하기
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("data")
    private T data;

    public static <T> CommonResponse<T> onSuccess(T data) {
        return new CommonResponse<>(true, "200", SuccessStatus.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResponse<T> of(BaseCode code, T data) {
        return new CommonResponse<>(true, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), data);
    }

    public static <T> CommonResponse<T> onNoContent() {
        return new CommonResponse<>(
                true,
                SuccessStatus.NO_CONTENT.getCode(), // 204 코드 가져오기
                SuccessStatus.NO_CONTENT.getMessage(), // "콘텐츠 없음" 메시지 가져오기
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
