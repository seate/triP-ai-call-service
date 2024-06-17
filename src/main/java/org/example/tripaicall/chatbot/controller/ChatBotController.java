package org.example.tripaicall.chatbot.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.tripaicall.chatbot.service.ChatBotService;
import org.example.tripaicall.openai.dto.Message;
import org.example.tripaicall.openai.dto.response.ChatGPTResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot")
@RequiredArgsConstructor
public class ChatBotController {
    private final ChatBotService chatBotService;

    @PostMapping("/chat/{userId}")
    public String chatAnalysis(@PathVariable String userId, @RequestParam String text) {
        ChatGPTResponse response = chatBotService.chatWithAi(userId, text);
        return response.getChoices().get(0).getMessage().getContent();
    }

    @GetMapping("/chat/{userId}")
    public List<Message> getChatMessages(@PathVariable String userId) {
        return chatBotService.getMessages(userId);
    }
}
