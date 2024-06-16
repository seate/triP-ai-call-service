package org.example.tripaicall.ImageTranslator.DTO.Response;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ImageTranslateResponseDTO {

    @Nullable
    private String mappingFailedOrNoForm;

    @Nullable
    private List<Menu> menus;
}
