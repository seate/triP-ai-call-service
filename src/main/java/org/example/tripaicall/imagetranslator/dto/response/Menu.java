package org.example.tripaicall.imagetranslator.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class Menu {

    private String name;

    private String price;

    private String description;

    private List<String> imageUrl;
}
