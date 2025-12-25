package com.explorertech.open_ai_integration.controller;

import com.explorertech.open_ai_integration.service.AIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    private final AIService aiChatService;

    public AiController(AIService aiChatService){
        this.aiChatService = aiChatService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String q){
        return aiChatService.ask(q);
    }
}
