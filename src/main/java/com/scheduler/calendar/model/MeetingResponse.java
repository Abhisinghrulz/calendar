package com.scheduler.calendar.model;

import com.scheduler.calendar.enums.MeetingResponseEnum;
import lombok.Data;

@Data
public class MeetingResponse {
    private MeetingResponseEnum userResponse;
}
