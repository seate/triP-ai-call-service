package org.example.tripaicall.ImageTranslator.Controller;

import lombok.RequiredArgsConstructor;
import org.example.tripaicall.ImageTranslator.DTO.Request.ImageTranslateRequestDTO;
import org.example.tripaicall.ImageTranslator.DTO.Response.ImageTranslateResponseDTO;
import org.example.tripaicall.ImageTranslator.Service.ImageTranslatorService;
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
