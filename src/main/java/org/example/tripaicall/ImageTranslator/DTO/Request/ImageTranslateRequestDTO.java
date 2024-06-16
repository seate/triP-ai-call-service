package org.example.tripaicall.ImageTranslator.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.tripaicall.ImageTranslator.Enum.ImageTranslateType;

@Data
public class ImageTranslateRequestDTO {

    @NotNull
    private ImageTranslateType imageTranslateType;


}
