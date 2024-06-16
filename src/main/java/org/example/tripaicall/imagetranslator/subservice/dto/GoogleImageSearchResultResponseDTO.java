package org.example.tripaicall.imagetranslator.subservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class GoogleImageSearchResultResponseDTO {

    private List<Item> items;
}
