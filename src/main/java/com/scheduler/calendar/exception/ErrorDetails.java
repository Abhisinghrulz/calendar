package com.scheduler.calendar.exception;

import java.util.Date;

public class ErrorDetails
{
    private final Date timeStamp;
    private final String message;
    private final String details;

    public ErrorDetails(Date timeStamp, String message, String details)
    {
        super();
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimeStamp(){ return timeStamp;}
    public String getMessage() {return message;}
    public String getDetails() { return details;}
}