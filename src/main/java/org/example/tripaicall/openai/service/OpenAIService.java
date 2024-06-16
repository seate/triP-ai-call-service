package org.example.tripaicall.openai.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.example.tripaicall.chatbot.repository.TestRepository;
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
    private final TestRepository testRepository;

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
        return testRepository.getMessages(userId);
    }

    public ChatGPTResponse chatWithAi(String userId, String chatText) {
        List<Message> messages = testRepository.getMessages(userId);
        Message chatMessage = new TextMessage("user", chatText);
        messages.add(chatMessage);
        ChatGPTRequest request = ChatGPTRequest.createChatBotRequest(apiModel, maxTokens, messages);
        ChatGPTResponse response = sendRequest(request);
        addMessage(messages, response.getChoices().get(0).getMessage());
        return response;
    }

    private void addMessage(List<Message> messages, Message message) {
        if(messages.size() > 10){
            messages.remove(1);
        }
        messages.add(message);
    }
}
