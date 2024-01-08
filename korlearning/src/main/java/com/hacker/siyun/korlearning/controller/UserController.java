package com.hacker.siyun.korlearning.controller;

import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.dto.UserDTO;
import com.hacker.siyun.korlearning.dto.UsersDTO;
import com.hacker.siyun.korlearning.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/users")
    public ApiResponse<List<UsersDTO>> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("/users/{userId}")
    public ApiResponse<UserDTO> getVideoByVideoId(@PathVariable("userId") Long userId)
    {
        return userService.getUserByUserId(userId);
    }

}
