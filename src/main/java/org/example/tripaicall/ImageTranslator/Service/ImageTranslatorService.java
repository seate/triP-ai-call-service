package org.example.tripaicall.ImageTranslator.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.tripaicall.ImageTranslator.DTO.Request.ImageTranslateRequestDTO;
import org.example.tripaicall.ImageTranslator.DTO.Response.ImageTranslateResponseDTO;
import org.example.tripaicall.ImageTranslator.DTO.Response.Menu;
import org.example.tripaicall.ImageTranslator.Enum.ImageTranslateType;
import org.example.tripaicall.ImageTranslator.SubService.GoogleImageSearch.Service.GoogleImageSearchService;
import org.example.tripaicall.ImageTranslator.SubService.OpenAI.DTO.Response.ChatGPTResponse;
import org.example.tripaicall.ImageTranslator.SubService.OpenAI.service.OpenAIService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageTranslatorService {

    private final OpenAIService openAIService;

    private final GoogleImageSearchService googleImageSearchService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private ImageTranslateResponseDTO translateMenuImage(MultipartFile image) throws IOException {

        ChatGPTResponse chatGPTResponse = openAIService.imageAnalysis(image,
                "이 메뉴판 이미지를 한국어로 해석하고, json 형식으로 [{“name”: “번역된 메뉴 이름”, “price”: “번역된 가격”, “description”: “너가 작성한 간단한 설명”}, ...] 형식으로 반환해줘! json으로 응답을 받아야 하니까 그 외에 다른 정보는 넣지말아줘!");

        String content = chatGPTResponse.getChoices().getFirst().getMessage().getContent();

        try {
            ImageTranslateResponseDTO imageTranslateResponseDTO = objectMapper.readValue(content, ImageTranslateResponseDTO.class);

            for (Menu menu : imageTranslateResponseDTO.getMenus()) {
                String name = menu.getName();

                googleImageSearchService.googleImageSearch(name, 3);
            }


        } catch (Exception e) {
            return ImageTranslateResponseDTO.builder().mappingFailedString(content).build();
        }
    }
/*

    private ImageTranslateResponseDTO translateNoFormImage(MultipartFile image) {
        openAIService.imageAnalysis(image, "")

    }
*/

    public ImageTranslateResponseDTO translateImage(MultipartFile image, ImageTranslateRequestDTO requestDTO) throws IOException {



        return switch (requestDTO.getImageTranslateType()) {
            case ImageTranslateType.MENU -> translateMenuImage(image);
            //case ImageTranslateType.SIGN -> translateSignImage(image);
            default -> throw new RuntimeException("Unexpected value: " + requestDTO.getImageTranslateType());
        };
    }



}
