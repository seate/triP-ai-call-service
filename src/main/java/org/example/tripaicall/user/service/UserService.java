package org.example.tripaicall.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        List<Message> messages = user.getMessages();
        if (messages.size() <= 1) {
            return List.of();
        }
        return messages.subList(1, messages.size());
    }

    public String signUp(String userId) {
        Message initMessage = new TextMessage("system", "너는 아주 상냥하고 귀여운 말투로 답변해주는 착한 여고생이야!");
        List<Message> messages = new ArrayList<>(List.of(initMessage));
        User newUser = new User(userId, "User", messages);
        userRepository.save(newUser);
        return "ok";
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
