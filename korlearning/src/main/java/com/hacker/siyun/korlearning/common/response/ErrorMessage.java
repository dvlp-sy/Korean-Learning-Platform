package com.hacker.siyun.korlearning.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
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


    /**
     * Transcript
     */
    TRANSCRIPT_NOT_FOUND(NOT_FOUND, "자막이 존재하지 않습니다")

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
