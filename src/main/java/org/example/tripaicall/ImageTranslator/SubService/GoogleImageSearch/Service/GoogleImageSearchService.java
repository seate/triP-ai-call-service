package org.example.tripaicall.ImageTranslator.SubService.GoogleImageSearch.Service;

import jakarta.annotation.PostConstruct;
import org.example.tripaicall.ImageTranslator.SubService.GoogleImageSearch.DTO.GoogleImageSearchResultResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GoogleImageSearchService {

    @Value("${google.api.url}")
    private String googleApiUrl;
    @Value("${google.api.key}")
    private String googleApiKey;
    @Value("${google.cse.id}")
    private String googleCseId;

    private final String googleSearchType = "image";

    private RestClient restClient;

    @PostConstruct
    private void restClient() {
        this.restClient = RestClient.builder()
                .baseUrl(googleApiUrl)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public GoogleImageSearchResultResponseDTO googleImageSearch(String query, Integer imageCount) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", googleApiKey)
                        .queryParam("cx", googleCseId)
                        .queryParam("q", query)
                        .queryParam("searchType", googleSearchType)
                        .queryParam("num", imageCount)
                        .build())
                .retrieve()
                .body(GoogleImageSearchResultResponseDTO.class);
    }
}
