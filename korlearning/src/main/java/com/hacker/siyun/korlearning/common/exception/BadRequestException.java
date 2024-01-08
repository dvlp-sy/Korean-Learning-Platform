package com.hacker.siyun.korlearning.common.exception;

import com.hacker.siyun.korlearning.common.response.ErrorMessage;
import lombok.Getter;

@Getter
public class BadRequestException extends BaseException
{
    public BadRequestException(ErrorMessage error) {
        super(error, "[Exception] "+ error.getMessage());
    }

}
