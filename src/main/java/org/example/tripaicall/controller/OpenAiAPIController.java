package org.example.tripaicall.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.example.tripaicall.dto.response.ChatGPTResponse;
import org.example.tripaicall.service.AiCallService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OpenAiAPIController {
    private final AiCallService aiCallService;

    @PostMapping("/imageUrl")
    public String imageUrlAnalysis(@RequestParam String imageUrl, @RequestParam String requestText) {
        ChatGPTResponse response = aiCallService.imageUrlAnalysis(imageUrl, requestText);
        return response.getChoices().get(0).getMessage().getContent();
    }

    @PostMapping("/image")
    public String imageAnalysis(@RequestParam MultipartFile image, @RequestParam String requestText)
            throws IOException {
        ChatGPTResponse response = aiCallService.imageAnalysis(image, requestText);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
