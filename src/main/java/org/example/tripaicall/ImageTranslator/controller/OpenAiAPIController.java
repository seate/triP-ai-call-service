package org.example.tripaicall.ImageTranslator.controller;

import lombok.RequiredArgsConstructor;
import org.example.tripaicall.ImageTranslator.dto.response.ChatGPTResponse;
import org.example.tripaicall.ImageTranslator.service.AiCallService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
