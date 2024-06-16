package org.example.tripaicall.imagetranslator.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.tripaicall.imagetranslator.util.ImageTranslateType;

@Data
public class ImageTranslateRequestDTO {

    @NotNull
    private ImageTranslateType imageTranslateType;


}
