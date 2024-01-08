package com.hacker.siyun.korlearning.common.exception;

import com.hacker.siyun.korlearning.common.response.ErrorMessage;
import lombok.Getter;

@Getter
public class TokenForbiddenException extends BaseException
{
    public TokenForbiddenException(ErrorMessage error) {
        super(error, "[TokenForbiddenException] "+ error.getMessage());
    }

}
