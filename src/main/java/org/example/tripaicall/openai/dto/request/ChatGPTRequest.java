package org.example.tripaicall.openai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tripaicall.openai.dto.Message;
import org.example.tripaicall.openai.dto.TextMessage;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatGPTRequest {
    @JsonProperty("model")
    private String model;
    @JsonProperty("messages")
    private List<Message> messages;
    @JsonProperty("max_tokens")
    private int maxTokens;

    public static ChatGPTRequest createImageRequest(String model, int maxTokens, String role, String requestText, String imageUrl) {
        TextContent textContent = new TextContent("text", requestText);
        ImageContent imageContent = new ImageContent("image_url", new ImageUrl(imageUrl));
        Message message = new ImageRequestMessage(role, List.of(textContent, imageContent));
        return ChatGPTRequest.builder()
                .model(model)
                .maxTokens(maxTokens)
                .messages(Collections.singletonList(message))
                .build();
    }

    public static ChatGPTRequest createTextRequest(String model, int maxTokens, String role, String requestText) {
        return ChatGPTRequest.builder()
                .model(model)
                .maxTokens(maxTokens)
                .messages(Collections.singletonList(new TextMessage(role, requestText)))
                .build();
    }
}
