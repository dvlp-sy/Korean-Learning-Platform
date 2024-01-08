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
    GET_INDIVIDUAL_USER_SUCCESS(OK, "개별 유저 조회에 성공했습니다"),

    /**
     * Video
     */
    UPLOAD_VIDEO_SUCCESS(OK, "비디오 업로드에 성공했습니다"),
    GET_ALL_VIDEOS_SUCCESS(OK, "전체 비디오 조회에 성공했습니다"),
    GET_VIDEO_SUCCESS(OK, "비디오 조회에 성공했습니다"),

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
