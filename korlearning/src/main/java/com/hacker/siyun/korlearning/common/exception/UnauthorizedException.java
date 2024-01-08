package com.hacker.siyun.korlearning.common.exception;

import com.hacker.siyun.korlearning.common.response.ErrorMessage;
import lombok.Getter;

@Getter
public class UnauthorizedException extends BaseException
{
    public UnauthorizedException(ErrorMessage error) {
        super(error, "[UnauthorizedException] "+ error.getMessage());
    }

}
