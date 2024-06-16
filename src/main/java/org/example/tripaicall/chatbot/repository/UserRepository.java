package org.example.tripaicall.chatbot.repository;

import org.example.tripaicall.chatbot.model.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface UserRepository extends CrudRepository<User, String>{
}
