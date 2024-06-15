package org.example.tripaicall.ImageTranslator.SubService.OpenAI.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private String role;
    private String content;
}