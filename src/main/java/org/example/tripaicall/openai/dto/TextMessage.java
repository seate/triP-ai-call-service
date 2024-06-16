package org.example.tripaicall.openai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TextMessage extends Message {
    private String content;

    public TextMessage(String role, String content) {
        super(role);
        this.content = content;
    }
}
