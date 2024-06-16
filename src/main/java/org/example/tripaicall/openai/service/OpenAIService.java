package org.example.tripaicall.openai.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.example.tripaicall.chatbot.model.User;
import org.example.tripaicall.chatbot.repository.UserRepository;
import org.example.tripaicall.openai.dto.Message;
import org.example.tripaicall.openai.dto.TextMessage;
import org.example.tripaicall.openai.dto.request.ChatGPTRequest;
import org.example.tripaicall.openai.dto.response.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OpenAIService {
    @Value("${openai.model}")
    private String apiModel;
    @Value("${openai.api.url}")
    private String apiUrl;
    @Value("${openai.max.tokens}")
    private Integer maxTokens;
    @Value("${openai.api.key}")
    private String openAiApiKey;

    private RestClient restClient;
    private final UserRepository userRepository;

    @PostConstruct
    private void restClient() {
        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + openAiApiKey)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private ChatGPTResponse sendRequest(ChatGPTRequest request) {
        return restClient.post()
                .body(request)
                .retrieve()
                .body(ChatGPTResponse.class);
    }

    public ChatGPTResponse imageUrlAnalysis(String imageUrl, String requestText) {
        ChatGPTRequest request = ChatGPTRequest.createImageRequest(apiModel, maxTokens, "user", requestText, imageUrl);
        return sendRequest(request);
    }

    public ChatGPTResponse imageAnalysis(MultipartFile image, String requestText) throws IOException {
        String base64Image = Base64.encodeBase64String(image.getBytes());
        String imageUrl = "data:image/jpeg;base64," + base64Image;
        ChatGPTRequest request = ChatGPTRequest.createImageRequest(apiModel, maxTokens, "user", requestText, imageUrl);
        return sendRequest(request);
    }

    public ChatGPTResponse textAnalysis(String requestText) {
        ChatGPTRequest request = ChatGPTRequest.createTextRequest(apiModel, maxTokens, "user", requestText);
        return sendRequest(request);
    }

    public List<Message> getMessages(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        List<Message> messages = user.getMessages();
        if(messages.size()<=1) {
            return List.of();
        }
        return messages.subList(1, messages.size());
    }

    public ChatGPTResponse chatWithAi(String userId, String chatText) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Message> messages = user.getMessages();
        messages.add(new TextMessage("user", chatText));
        List<Message> recentMessages = getRecentMessages(messages);

        ChatGPTRequest request = ChatGPTRequest.createChatBotRequest(apiModel, maxTokens, recentMessages);
        ChatGPTResponse response = sendRequest(request);
        messages.add(response.getChoices().get(0).getMessage());

        user.setMessages(messages);
        userRepository.save(user);
        return response;
    }

    private List<Message> getRecentMessages(List<Message> messages) {
        if(messages.size() > 10){
            List<Message> recentMessages = new ArrayList<>(List.of(messages.getFirst()));
            recentMessages.addAll(messages.subList(messages.size() - 9, messages.size()));
            return recentMessages;
        } else {
            return messages;
        }
    }

    public String signUp(String userId) {
        Message initMessage = new TextMessage("system", "너는 아주 상냥하고 귀여운 말투로 답변해주는 착한 여고생이야!");
        List<Message> messages = new ArrayList<>(List.of(initMessage));
        User newUser = new User(userId, "User", messages);
        userRepository.save(newUser);
        return "ok";
    }
}
