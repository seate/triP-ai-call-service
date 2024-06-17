package org.example.tripaicall.user.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.example.tripaicall.openai.dto.Message;

public class MessageConverter implements DynamoDBTypeConverter<String, List<Message>> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(List<Message> messages) {
        try {
            return objectMapper.writeValueAsString(messages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting messages to JSON", e);
        }
    }

    @Override
    public List<Message> unconvert(String json) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Message.class));
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to messages", e);
        }
    }
}
