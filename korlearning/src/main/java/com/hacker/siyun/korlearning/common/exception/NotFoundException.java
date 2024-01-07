package com.hacker.siyun.korlearning.common.exception;

import com.hacker.siyun.korlearning.common.response.ErrorMessage;
import lombok.Getter;

@Getter
public class NotFoundException extends BaseException
{
    public NotFoundException(ErrorMessage error) {
        super(error, "[NotFoundException] "+ error.getMessage());
    }

}
