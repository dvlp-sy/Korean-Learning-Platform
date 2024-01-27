package com.hacker.siyun.korlearning.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

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
    PATCH_VIDEO_SUCCESS(OK, "비디오 정보 업데이트에 성공했습니다"),
    DELETE_VIDEO_SUCCESS(NO_CONTENT, "비디오 삭제에 성공했습니다"),

    /**
     * Transcript
     */
    GET_ALL_TRANSCRIPTS_SUCCESS(OK, "전체 자막 반환에 성공했습니다"),
    GET_TRANSCRIPT_SUCCESS(OK, "개별 자막 반환에 성공했습니다")

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
