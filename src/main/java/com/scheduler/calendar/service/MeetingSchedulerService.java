package com.scheduler.calendar.service;

import com.scheduler.calendar.exception.MeetingException;
import com.scheduler.calendar.model.MeetingDAO;
import com.scheduler.calendar.model.MeetingEvent;
import com.scheduler.calendar.model.MeetingRoom;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * MeetingRoomSchedulerService takes the responsibility of checking the availability and to schedule the meeting
 */
@Service
public class MeetingSchedulerService {
    private int MAX_MEETING_ROOMS;
    private List<MeetingRoom> meetingRoomList;
    private int idCounter;
    private List<MeetingDAO> history;
    private int maxMeetingRooms = 10;
    private Map<String, List<MeetingRoom>> meetingsRoomMap = new HashMap<>();

    /**
     * Init Meeting Rooms
     */
    @PostConstruct
    private void initMeetingRooms() {
        MAX_MEETING_ROOMS = maxMeetingRooms;
        List<String> locations = Arrays.asList("Delhi", "Bangalore", "Mumbai");
        meetingRoomList = new ArrayList<>();
        idCounter = 0;
        history = new ArrayList<>();
        locations.stream().forEach(location -> {
        for (int i = 0; i < MAX_MEETING_ROOMS; i++) {
            MeetingRoom room = new MeetingRoom(location+i);
            meetingRoomList.add(room);
        }});
        locations.stream().forEach(location -> meetingsRoomMap.put(location, meetingRoomList));
    }

    /**
     * Book Meeting
     * @param meetingEvent MeetingEvent
     * @return MeetingDAO
     */
    public synchronized MeetingDAO bookMeeting(MeetingEvent meetingEvent) {
        for (MeetingRoom room : meetingsRoomMap.get(meetingEvent.getLocation())) {
            if (room.isAvailable(meetingEvent.getStartTime(), meetingEvent.getEndTime())) {
                int counter = idCounter++;
                MeetingDAO meetingDAO = room.bookMeeting(meetingEvent.getLocation()+ counter, meetingEvent);
                history.add(meetingDAO);
                return meetingDAO;
            }
        }
        throw new MeetingException("Meeting Room Unavailable");
    }


    public Map<String, List<MeetingRoom>> getMeetingsRoomMap(){
        return meetingsRoomMap;
    }

    public List<MeetingDAO> getHistoryOfMeetings(int size) {
        int historySize = history.size();
        int finalSize = size < historySize ? historySize : size;
        return new ArrayList<>(history.subList(historySize - finalSize, historySize));
    }
}
