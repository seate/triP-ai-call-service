package org.example.tripaicall.service;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.example.tripaicall.dto.request.ChatGPTRequest;
import org.example.tripaicall.dto.response.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AiCallService {
    @Value("${openai.model}")
    private String apiModel;
    @Value("${openai.api.url}")
    private String apiUrl;
    private static final Integer MAX_TOKENS = 300;

    private final RestTemplate template;

    public ChatGPTResponse imageUrlAnalysis(String imageUrl, String requestText) {
        ChatGPTRequest request = new ChatGPTRequest(apiModel, MAX_TOKENS, requestText, imageUrl);
        return template.postForObject(apiUrl, request, ChatGPTResponse.class);
    }

    public ChatGPTResponse imageAnalysis(MultipartFile image, String requestText) throws IOException {
        String base64Image = Base64.encodeBase64String(image.getBytes());
        String imageUrl = "data:image/jpeg;base64," + base64Image;
        ChatGPTRequest request = new ChatGPTRequest(apiModel, MAX_TOKENS, requestText, imageUrl);
        return template.postForObject(apiUrl, request, ChatGPTResponse.class);
    }
}
