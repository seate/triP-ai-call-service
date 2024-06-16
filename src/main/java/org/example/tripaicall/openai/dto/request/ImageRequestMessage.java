package org.example.tripaicall.openai.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageRequestMessage extends RequestMessage {
    private List<Content> content;

    public ImageRequestMessage(String role, List<Content> content) {
        super(role);
        this.content = content;
    }
}
