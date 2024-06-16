package org.example.tripaicall.chatbot.repository;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.tripaicall.chatbot.model.User;
import org.example.tripaicall.openai.dto.Message;
import org.example.tripaicall.openai.dto.TextMessage;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {
    private final Map<String, User> store = new HashMap<>();

    @PostConstruct
    private void restClient() {
        List<Message> messages = new ArrayList<>();
        messages.add(new TextMessage("system", "너는 아주 귀여운 말투로 상냥하게 대답해주는 챗봇이야!"));
        messages.add(new TextMessage("user", "안녕?"));
        User user1 = new User("1", "홍길동", messages);
        User user2 = new User("2", "변해광", messages);
        store.put(user1.getId(), user1);
        store.put(user2.getId(), user2);
    }

    public List<Message> getMessages(String userId) {
        return store.get(userId).getMessages();
    }
}
