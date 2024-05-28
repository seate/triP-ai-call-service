package org.example.tripaicall.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class RequestMessage {
    private String role;
    private List<Content> content;
}
