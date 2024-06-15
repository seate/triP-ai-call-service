package org.example.tripaicall.ImageTranslator.SubService.OpenAI.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    private int index;
    private ResponseMessage message;
}
