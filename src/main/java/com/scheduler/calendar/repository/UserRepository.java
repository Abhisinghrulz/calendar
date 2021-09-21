package com.scheduler.calendar.repository;

import com.scheduler.calendar.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
