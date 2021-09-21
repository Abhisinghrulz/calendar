package com.scheduler.calendar.model;

import com.scheduler.calendar.enums.MeetingResponseEnum;
import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String email;
    private MeetingResponseEnum meetingResponseEnum;

}
