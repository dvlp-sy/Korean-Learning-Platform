package com.hacker.siyun.korlearning.service;

import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.common.exception.NotFoundException;
import com.hacker.siyun.korlearning.common.response.ErrorMessage;
import com.hacker.siyun.korlearning.common.response.SuccessMessage;
import com.hacker.siyun.korlearning.dto.UserDTO;
import com.hacker.siyun.korlearning.dto.UsersDTO;
import com.hacker.siyun.korlearning.model.User;
import com.hacker.siyun.korlearning.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.stream.Collector;

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

    public ApiResponse<UserDTO> getUserByUserId(Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

        return ApiResponse.success(SuccessMessage.GET_INDIVIDUAL_USER_SUCCESS, UserDTO.build(user));
    }

}
