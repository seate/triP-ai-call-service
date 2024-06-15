package org.example.tripaicall.ImageTranslator.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TextRequestMessage extends RequestMessage {
    private String content;

    public TextRequestMessage(String role, String content) {
        super(role);
        this.content = content;
    }
}
