package com.scheduler.calendar.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MeetingRoom {
    private String id;
    List<MeetingDAO> meetingDAOS;

    public MeetingRoom(String id) {
        this.id = id;
        meetingDAOS = new ArrayList<>();
    }

    /**
     * Is Meeting Room Available
     * @param startTime
     * @param endTime
     * @return
     */
    public boolean isAvailable(long startTime, long endTime) {
        for (MeetingDAO meetingDAO : meetingDAOS) {
            if (startTime >= meetingDAO.getStartTime() && startTime <= meetingDAO.getEndTime() ||
                    endTime >= meetingDAO.getStartTime() && endTime <= meetingDAO.getEndTime()) return false;
        }
        return true;
    }

    /**
     *
     * @param id
     * @param meetingEvent
     * @return
     */
    public MeetingDAO bookMeeting(String id, MeetingEvent meetingEvent) {
        MeetingDAO meetingDAO = new MeetingDAO(id, meetingEvent.getStartTime(), meetingEvent.getEndTime(),
                meetingEvent.getLocation(), meetingEvent.getUserList(), meetingEvent.getOwner(), meetingEvent.getTitle());
        meetingDAOS.add(meetingDAO);
        return meetingDAO;
    }
}
