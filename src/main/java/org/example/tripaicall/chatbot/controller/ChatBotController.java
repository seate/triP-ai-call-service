package org.example.tripaicall.chatbot.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.tripaicall.chatbot.service.ChatBotService;
import org.example.tripaicall.openai.dto.Message;
import org.example.tripaicall.openai.dto.response.ChatGPTResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot")
@RequiredArgsConstructor
public class ChatBotController {
    private final ChatBotService chatBotService;

    @PostMapping("/chat")
    public String chatMessages(@RequestParam String userId, @RequestParam String text) {
        ChatGPTResponse response = chatBotService.chatWithAi(userId, text);
        return response.getChoices().get(0).getMessage().getContent();
    }

    @GetMapping("/chat")
    public List<Message> getAllChatMessages(@RequestParam String userId) {
        return chatBotService.getMessages(userId);
    }
}
