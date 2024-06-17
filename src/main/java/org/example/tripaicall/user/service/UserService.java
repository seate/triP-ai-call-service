package org.example.tripaicall.user.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.tripaicall.user.repository.UserRepository;
import org.example.tripaicall.openai.dto.Message;
import org.example.tripaicall.openai.dto.TextMessage;
import org.example.tripaicall.user.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<Message> getMessages(String userId) {
        User user = getUserById(userId);
        List<Message> messages = user.getMessages();
        if (messages.size() <= 1) {
            return List.of();
        }
        return messages.subList(1, messages.size());
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseGet(() -> init(userId));
    }

    public User init(String userId) {
        Message initMessage = new TextMessage("system", "너는 아주 상냥하고 귀여운 말투로 답변해주는 착한 여고생이야!");
        List<Message> messages = new ArrayList<>(List.of(initMessage));
        User newUser = new User(userId, "User", messages);
        userRepository.save(newUser);
        return newUser;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
