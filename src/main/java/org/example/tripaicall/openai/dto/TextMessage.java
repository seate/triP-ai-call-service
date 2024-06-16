package org.example.tripaicall.openai.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class TextMessage extends Message {
    @DynamoDBAttribute(attributeName = "content")
    private String content;

    public TextMessage(String role, String content) {
        super(role);
        this.content = content;
    }
}
