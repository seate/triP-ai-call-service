package org.example.tripaicall.imagetranslator.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.example.tripaicall.imagetranslator.dto.request.ImageTranslateRequestDTO;
import org.example.tripaicall.imagetranslator.dto.response.ImageTranslateResponseDTO;
import org.example.tripaicall.imagetranslator.dto.response.Menu;
import org.example.tripaicall.imagetranslator.util.ImageTranslateType;
import org.example.tripaicall.imagetranslator.subservice.dto.GoogleImageSearchResultResponseDTO;
import org.example.tripaicall.imagetranslator.subservice.dto.Item;
import org.example.tripaicall.imagetranslator.subservice.service.GoogleImageSearchService;
import org.example.tripaicall.openai.dto.response.ChatGPTResponse;
import org.example.tripaicall.openai.service.OpenAIClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ImageTranslatorService {

    private final OpenAIClientService openAIService;

    private final GoogleImageSearchService googleImageSearchService;

    private final Gson gson = new Gson();

    private final Type menuListType = new TypeToken<List<Menu>>() {}.getType();

    private final Integer IMAGE_SEARCH_COUNT = 3;


    private ImageTranslateResponseDTO translateMenuImage(MultipartFile image) throws IOException {
        ChatGPTResponse chatGPTResponse = openAIService.requestImageAnalysis(image,
                "이 메뉴판 이미지를 한국어로 해석하고, json 형식으로 [{“name”: “번역된 메뉴 이름”, “price”: “번역된 가격”, “description”: “너가 작성한 간단한 설명”}, ...] 형식으로 반환해줘! json으로 응답을 받아야 하니까 그 외에 다른 정보는 넣지말아줘!");

        String content = chatGPTResponse.getChoices().getFirst().getMessage().getContent();
        try {
            if (content.contains("json"))
                content = content.substring(content.indexOf("["), content.lastIndexOf("]") + 1);

            List<Menu> menus = gson.fromJson(content, menuListType);
            ImageTranslateResponseDTO imageTranslateResponseDTO = ImageTranslateResponseDTO.builder().menus(menus).build();


            ExecutorService virtualThreadPerTaskExecutor = Executors.newVirtualThreadPerTaskExecutor();

            IntStream.range(0, imageTranslateResponseDTO.getMenus().size())
                    .forEach(i -> {
                        Menu menu = imageTranslateResponseDTO.getMenus().get(i);

                        Future<GoogleImageSearchResultResponseDTO> imageResponseFuture = virtualThreadPerTaskExecutor
                                .submit(() -> googleImageSearchService.googleImageSearch(menu.getName(), IMAGE_SEARCH_COUNT));

                        GoogleImageSearchResultResponseDTO googleImageSearchResultResponseDTO = null;
                        try {
                            googleImageSearchResultResponseDTO = imageResponseFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }

                        List<String> images = googleImageSearchResultResponseDTO.getItems().stream().map(Item::getLink).toList();
                        menu.setImageUrl(images);
                    });

            virtualThreadPerTaskExecutor.shutdown();

            return imageTranslateResponseDTO;
        } catch (Exception e) {
            return ImageTranslateResponseDTO.builder().mappingFailedOrNoForm(content).build();
        }
    }

    private ImageTranslateResponseDTO translateNoFormImage(MultipartFile image) throws IOException {
        ChatGPTResponse chatGPTResponse = openAIService.requestImageAnalysis(image,
                "이 이미지가 어떤 이미지인지 알려주고, 이 이미지의 글자를 한국어로 번역해줘! 번역된 글자를 반환해줘!");
        String content = chatGPTResponse.getChoices().getFirst().getMessage().getContent();

        return ImageTranslateResponseDTO.builder().mappingFailedOrNoForm(content).build();
    }


    public ImageTranslateResponseDTO translateImage(MultipartFile image, ImageTranslateRequestDTO requestDTO) throws IOException {
        return switch (requestDTO.getImageTranslateType()) {
            case ImageTranslateType.MENU -> translateMenuImage(image);
            case ImageTranslateType.OTHER -> translateNoFormImage(image);
        };
    }
}
