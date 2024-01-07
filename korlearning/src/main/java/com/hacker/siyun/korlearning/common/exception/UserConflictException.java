package com.hacker.siyun.korlearning.common.exception;

import com.hacker.siyun.korlearning.common.response.ErrorMessage;
import lombok.Getter;

@Getter
public class UserConflictException extends BaseException
{
    public UserConflictException(ErrorMessage error) {
        super(error, "[UserConflictException] "+ error.getMessage());
    }

}
