package com.hacker.siyun.korlearning.controller;

import com.hacker.siyun.korlearning.service.EvaluationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluationController
{
    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) { this.evaluationService = evaluationService; }
}
