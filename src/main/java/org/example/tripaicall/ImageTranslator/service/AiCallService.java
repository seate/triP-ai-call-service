package org.example.tripaicall.ImageTranslator.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.example.tripaicall.ImageTranslator.dto.request.ChatGPTRequest;
import org.example.tripaicall.ImageTranslator.dto.response.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AiCallService {
    @Value("${openai.model}")
    private String apiModel;
    @Value("${openai.api.url}")
    private String apiUrl;
    @Value("${openai.max.tokens}")
    private Integer maxTokens;
    @Value("${openai.api.key}")
    private String openAiApiKey;

    private RestClient restClient;

    @PostConstruct
    private void restClient() {
        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + openAiApiKey)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public ChatGPTResponse imageUrlAnalysis(String imageUrl, String requestText) {
        ChatGPTRequest request = ChatGPTRequest.createImageRequest(apiModel, maxTokens, "user", requestText, imageUrl);

        return restClient.post()
                .body(request)
                .retrieve()
                .body(ChatGPTResponse.class);
    }

    public ChatGPTResponse imageAnalysis(MultipartFile image, String requestText) throws IOException {
        String base64Image = Base64.encodeBase64String(image.getBytes());
        String imageUrl = "data:image/jpeg;base64," + base64Image;
        ChatGPTRequest request = ChatGPTRequest.createImageRequest(apiModel, maxTokens, "user", requestText, imageUrl);

        return restClient.post()
                .body(request)
                .retrieve()
                .body(ChatGPTResponse.class);
    }

    public ChatGPTResponse textAnalysis(String requestText) {
        ChatGPTRequest request = ChatGPTRequest.createTextRequest(apiModel, maxTokens, "user", requestText);

        return restClient.post()
                .body(request)
                .retrieve()
                .body(ChatGPTResponse.class);
    }
}
