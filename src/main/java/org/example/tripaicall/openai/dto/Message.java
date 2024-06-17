package org.example.tripaicall.openai.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tripaicall.user.util.MessageDeserializer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
@JsonDeserialize(using = MessageDeserializer.class)
public abstract class Message {
    @DynamoDBAttribute(attributeName = "role")
    private String role;
}
