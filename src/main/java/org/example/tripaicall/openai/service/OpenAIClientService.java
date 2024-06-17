package org.example.tripaicall.openai.service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.example.tripaicall.user.repository.UserRepository;
import org.example.tripaicall.openai.dto.request.ChatGPTRequest;
import org.example.tripaicall.openai.dto.response.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service
@Getter
@RequiredArgsConstructor
public class OpenAIClientService {
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

    public ChatGPTResponse sendRequest(ChatGPTRequest request) {
        return restClient.post()
                .body(request)
                .retrieve()
                .body(ChatGPTResponse.class);
    }

    public ChatGPTResponse requestImageAnalysis(MultipartFile image, String requestText) throws IOException {
        String base64Image = Base64.encodeBase64String(image.getBytes());
        String imageUrl = "data:image/jpeg;base64," + base64Image;
        ChatGPTRequest request = ChatGPTRequest.createImageRequest(apiModel, maxTokens, "user", requestText, imageUrl);
        return sendRequest(request);
    }

    public ChatGPTResponse requestTextAnalysis(String requestText) {
        ChatGPTRequest request = ChatGPTRequest.createTextRequest(apiModel, maxTokens, "user", requestText);
        return sendRequest(request);
    }
}
