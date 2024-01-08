package com.hacker.siyun.korlearning.service;

import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.common.response.SuccessMessage;
import com.hacker.siyun.korlearning.dto.UsersDTO;
import com.hacker.siyun.korlearning.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public ApiResponse<List<UsersDTO>> getAllUsers()
    {
        List<UsersDTO> usersDTOList = userRepository.findAll().stream()
                .map(UsersDTO::build)
                .toList();

        return ApiResponse.success(SuccessMessage.GET_ALL_USERS_SUCCESS, usersDTOList);
    }
}
