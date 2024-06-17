package org.example.tripaicall.user.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tripaicall.user.util.MessageConverter;
import org.example.tripaicall.openai.dto.Message;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "TRIP_USER")
public class User {
    private String id;
    private String name;
    private List<Message> messages;

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    @DynamoDBTypeConverted(converter = MessageConverter.class)
    @DynamoDBAttribute(attributeName = "messages")
    public List<Message> getMessages() {
        return messages;
    }
}
