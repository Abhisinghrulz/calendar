package com.scheduler.calendar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MeetingEvent {
    private long startTime;
    private long endTime;
    private String location;
    private String owner;
    private List<User> userList;
    private String title;
}
