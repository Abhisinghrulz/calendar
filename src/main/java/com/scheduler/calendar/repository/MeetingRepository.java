package com.scheduler.calendar.repository;

import com.scheduler.calendar.model.MeetingDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeetingRepository extends MongoRepository<MeetingDAO, String> {
}
