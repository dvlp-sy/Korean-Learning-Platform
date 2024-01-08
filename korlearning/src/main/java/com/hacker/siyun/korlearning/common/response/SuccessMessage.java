package com.hacker.siyun.korlearning.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessMessage
{
    /**
    * User
     */
    GET_ALL_USERS_SUCCESS(OK, "전체 유저 조회에 성공했습니다"),

    /**
     * Video
     */

    /**
     * Transcript
     */
    GET_TRANSCRIPTS_SUCCESS(OK, "자막 반환에 성공했습니다"),

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
