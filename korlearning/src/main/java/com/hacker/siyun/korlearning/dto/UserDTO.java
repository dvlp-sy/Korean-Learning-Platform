package com.hacker.siyun.korlearning.dto;

import com.hacker.siyun.korlearning.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDTO
{
    private final Long userId;
    private final String nickname;
    private final String social;
    private final String profileImageUrl;
    private final String introduction;

    @Builder(access = AccessLevel.PRIVATE)
    private UserDTO(Long userId, String nickname, String social, String profileImageUrl, String introduction)
    {
        this.userId = userId;
        this.nickname = nickname;
        this.social = social;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
    }

    public static UserDTO build(User user)
    {
        return UserDTO.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .social(user.getSocial())
                .profileImageUrl(user.getProfileImageUrl())
                .introduction(user.getIntroduction())
                .build();
    }
}
