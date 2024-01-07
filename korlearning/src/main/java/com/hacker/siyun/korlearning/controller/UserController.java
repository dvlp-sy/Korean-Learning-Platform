package com.hacker.siyun.korlearning.controller;

import com.hacker.siyun.korlearning.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }
}
