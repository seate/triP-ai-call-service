package org.example.tripaicall.service;

import lombok.RequiredArgsConstructor;
import org.example.tripaicall.dto.request.ChatGPTRequest;
import org.example.tripaicall.dto.response.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
