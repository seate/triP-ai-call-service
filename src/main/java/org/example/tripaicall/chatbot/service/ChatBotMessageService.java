package org.example.tripaicall.chatbot.service;

import java.util.ArrayList;
import java.util.List;
import org.example.tripaicall.openai.dto.Message;
import org.example.tripaicall.openai.dto.TextMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatBotMessageService {
    public List<Message> getRecentMessages(List<Message> messages) {
        if (messages.size() > 10) {
            List<Message> recentMessages = new ArrayList<>(List.of(messages.get(0)));
            recentMessages.addAll(messages.subList(messages.size() - 9, messages.size()));
            return recentMessages;
        } else {
            return messages;
        }
    }

    public void addMessage(List<Message> messages, String role, String content) {
        messages.add(new TextMessage(role, content));
    }
}
