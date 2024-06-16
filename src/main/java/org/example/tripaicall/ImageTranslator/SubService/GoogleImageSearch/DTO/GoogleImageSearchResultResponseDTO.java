package org.example.tripaicall.ImageTranslator.SubService.GoogleImageSearch.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GoogleImageSearchResultResponseDTO {

    private List<Item> items;
}
