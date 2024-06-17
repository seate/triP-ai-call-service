package org.example.tripaicall.user.repository;

import org.example.tripaicall.user.model.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface UserRepository extends CrudRepository<User, String>{
}
