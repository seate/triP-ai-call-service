package org.example.tripaicall.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTRequest {
    @JsonProperty("model")
    private String model;
    @JsonProperty("messages")
    private List<RequestMessage> messages;
    @JsonProperty("max_tokens")
    private int maxTokens;

    public ChatGPTRequest(String model, int maxTokens, String requestText, String imageUrl) {
        this.model = model;
        this.maxTokens = maxTokens;

        TextContent textContent = new TextContent("text", requestText);
        ImageContent imageContent = new ImageContent("image_url", new ImageUrl(imageUrl));

        RequestMessage message = new RequestMessage("user", List.of(textContent, imageContent));
        this.messages = Collections.singletonList(message);
    }
}
