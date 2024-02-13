package com.hacker.siyun.korlearning.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage
{
    /**
     * User
     */
    USER_NOT_FOUND(NOT_FOUND, "유저가 존재하지 않습니다"),

    /**
     * Video
     */
    CATEGORY_NOT_FOUND(NOT_FOUND, "카테고리가 존재하지 않습니다"),
    VIDEO_NOT_FOUND(NOT_FOUND, "비디오가 존재하지 않습니다"),


    /**
     * Transcript
     */
    TRANSCRIPT_NOT_FOUND(NOT_FOUND, "자막이 존재하지 않습니다"),
    VIDEO_TRANSCRIPT_NOT_FOUND(NOT_FOUND, "해당 비디오에 해당 자막이 존재하지 않습니다"),
    COUNTRY_NOT_FOUND(NOT_FOUND, "해당 언어는 지원하지 않습니다"),

    /**
     * Evaluation
     */

    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
