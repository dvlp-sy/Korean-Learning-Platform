package com.hacker.siyun.korlearning.service;

import com.hacker.siyun.korlearning.repository.EvaluationRepository;
import com.hacker.siyun.korlearning.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService
{
    private final EvaluationRepository evaluationRepository;
    private final UserRepository userRepository;

    public EvaluationService(
            EvaluationRepository evaluationRepository,
            UserRepository userRepository
    )
    {
        this.evaluationRepository = evaluationRepository;
        this.userRepository = userRepository;
    }
}
