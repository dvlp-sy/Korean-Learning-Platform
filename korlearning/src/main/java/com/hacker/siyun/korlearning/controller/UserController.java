package com.hacker.siyun.korlearning.controller;

import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.dto.UserDTO;
import com.hacker.siyun.korlearning.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/users/{userId}")
    public ApiResponse<UserDTO> getUserByUserId(@PathVariable Long userId)
    {
        return userService.getUserByUserId(userId);
    }
}
