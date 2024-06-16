package org.example.tripaicall.imagetranslator.controller;

import lombok.RequiredArgsConstructor;
import org.example.tripaicall.imagetranslator.dto.request.ImageTranslateRequestDTO;
import org.example.tripaicall.imagetranslator.dto.response.ImageTranslateResponseDTO;
import org.example.tripaicall.imagetranslator.service.ImageTranslatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image/translate")
@RequiredArgsConstructor
public class ImageTranslatorController {

    private final ImageTranslatorService imageTranslatorService;

    @PostMapping
    public ImageTranslateResponseDTO imageAnalysis(@RequestPart MultipartFile image, @RequestPart ImageTranslateRequestDTO requestDTO) throws IOException {
        return imageTranslatorService.translateImage(image, requestDTO);
    }
}
