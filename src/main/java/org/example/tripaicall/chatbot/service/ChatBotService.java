package org.example.tripaicall.chatbot.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.tripaicall.openai.dto.Message;
import org.example.tripaicall.openai.dto.request.ChatGPTRequest;
import org.example.tripaicall.openai.dto.response.ChatGPTResponse;
import org.example.tripaicall.openai.service.OpenAIClientService;
import org.example.tripaicall.user.model.User;
import org.example.tripaicall.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatBotService {
    private final OpenAIClientService openAIService;
    private final UserService userService;
    private final ChatBotMessageService messageService;

    public ChatGPTResponse chatWithAi(String userId, String chatText) {
        User user = userService.getUserById(userId);
        List<Message> messages = user.getMessages();
        messageService.addMessage(messages, "user", chatText);
        List<Message> recentMessages = messageService.getRecentMessages(messages);

        ChatGPTRequest request = ChatGPTRequest.createMessageListRequest(openAIService.getApiModel(), openAIService.getMaxTokens(), recentMessages);
        ChatGPTResponse response = openAIService.sendRequest(request);
        messageService.addMessage(messages, "bot", response.getChoices().get(0).getMessage().getContent());

        user.setMessages(messages);
        userService.saveUser(user);
        return response;
    }

    public List<Message> getMessages(String userId) {
        return userService.getMessages(userId);
    }

    public String signUp(String userId) {
        return userService.signUp(userId);
    }
}
