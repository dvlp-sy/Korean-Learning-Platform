package com.hacker.siyun.korlearning.service;

import com.hacker.siyun.korlearning.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }
}
