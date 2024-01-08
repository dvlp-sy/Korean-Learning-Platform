package com.hacker.siyun.korlearning.dto;

import com.hacker.siyun.korlearning.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UsersDTO
{
    private final Long userId;
    private final String nickname;

    @Builder(access = AccessLevel.PRIVATE)
    private UsersDTO(Long userId, String nickname)
    {
        this.userId = userId;
        this.nickname = nickname;
    }

    public static UsersDTO build(User user)
    {
        return UsersDTO.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .build();
    }
}
