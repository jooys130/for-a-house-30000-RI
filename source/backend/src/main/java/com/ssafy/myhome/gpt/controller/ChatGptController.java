package com.ssafy.myhome.gpt.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.myhome.gpt.model.ChatGptResponse;
import com.ssafy.myhome.gpt.model.QuestionRequest;
import com.ssafy.myhome.gpt.service.ChatGptService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/chat-gpt")
@Api("APT 컨트롤러 API V1")
public class ChatGptController {

    private final ChatGptService chatGptService;

    public ChatGptController(ChatGptService chatGptService) {

        this.chatGptService = chatGptService;
    }

    @PostMapping("/question")
    public ChatGptResponse sendQuestion(@RequestBody QuestionRequest requestDto) {
        return chatGptService.askQuestion(requestDto);
    }
}