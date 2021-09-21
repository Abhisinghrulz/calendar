package com.scheduler.calendar.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Setter
@Getter
@ToString
public class MeetingDAO {
    private String id;
    private long startTime;
    private long endTime;
    private String location;
    private List<User> usersList;
    private String owner;
    private String title;

    public MeetingDAO(String id, long startTime, long endTime, String location, List<User> usersList, String owner, String title) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.usersList = usersList;
        this.owner = owner;
        this.title = title;
    }
}
