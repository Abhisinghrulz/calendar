package com.scheduler.calendar.service;

import com.scheduler.calendar.enums.MeetingResponseEnum;
import com.scheduler.calendar.exception.ResourceNotFoundException;
import com.scheduler.calendar.model.MeetingDAO;
import com.scheduler.calendar.model.MeetingEvent;
import com.scheduler.calendar.model.User;
import com.scheduler.calendar.repository.MeetingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    @Autowired
    MeetingSchedulerService meetingSchedulerService;

    @Autowired
    MeetingRepository meetingRepository;

    public MeetingDAO scheduleMeeting(MeetingEvent meetingEvent) {
        MeetingDAO meetingDAO = meetingSchedulerService.bookMeeting(meetingEvent);
        meetingRepository.save(meetingDAO);
        return meetingDAO;
    }

    @DeleteMapping
    public void deleteMeeting(String meetingId) {
        meetingRepository.deleteById(meetingId);
    }


    /**
     * Get the invitee Details for a particular meeting
     * @param id MeetingId
     * @return List of users
     */
    public List<User> getInviteesDetails(String id) {
        MeetingDAO meetingDAO = meetingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));
        return meetingDAO.getUsersList();
    }

    /**
     * Get the meeting details
     * @param startTime StartTime
     * @param endTime EndTime
     * @return List of Meeting
     */
    public List<MeetingDAO> getMeetingDetails(long startTime, long endTime) {
        return meetingRepository.findAll().stream().filter(
                item -> item.getStartTime() >= startTime && item.getEndTime() <= endTime
        ).collect(Collectors.toList());
    }

    /**
     * Add User Response for the Meeting
     * @param meetingId
     * @param meetingResponseEnum
     * @param userId
     * @return
     */
    public User addUserMeetingResponse(String meetingId, MeetingResponseEnum meetingResponseEnum, String userId) {
        MeetingDAO meetingDAO = meetingRepository.findById(meetingId).orElseThrow(() -> new ResourceNotFoundException(""));
        User user = meetingDAO.getUsersList().stream().filter(user1 -> user1.getId().equals(userId)).collect(Collectors.toList()).get(0);
        user.setMeetingResponseEnum(meetingResponseEnum);
        meetingRepository.save(meetingDAO);
        return user;
    }

    /**
     * Find Out the Available meeting Rooms
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> availableMeetingRooms(long startTime, long endTime) {
        //Map<String, List<MeetingRoom>> meetingsRoomMap = meetingSchedulerService.getMeetingsRoomMap().entrySet().stream().map(item -> item.getValue()).
        List<String> meetingIds = meetingSchedulerService.getMeetingsRoomMap().values().stream().flatMap(List::stream).map(meetingRoom -> meetingRoom.getId()).collect(Collectors.toList());
        meetingRepository.findAll().stream().filter(
                item -> item.getStartTime() >= startTime && item.getEndTime() <= endTime
        ).forEach(item -> meetingIds.remove(item.getId()));
        return meetingIds;
    }

    /**
     * Update the Meeting
     * @param meetingId
     * @param meetingEvent
     * @return
     */
    public MeetingDAO updateMeeting(String meetingId, MeetingEvent meetingEvent) {
        return meetingRepository.findById(meetingId)
                .map(meeting -> {
                    BeanUtils.copyProperties(meetingEvent, meeting, "id");
                    return meetingRepository.save(meeting);
                })
                .orElseGet(() -> {
                    MeetingDAO meetingDAO = new MeetingDAO(meetingId, meetingEvent.getStartTime(), meetingEvent.getEndTime(),
                            meetingEvent.getLocation(), meetingEvent.getUserList(), meetingEvent.getOwner(), meetingEvent.getTitle());
                    return meetingRepository.save(meetingDAO);
                });
    }

    /**
     * Admin Schedule Meeting
     * @param meetingEvent
     * @return
     */
    public MeetingDAO adminScheduleMeeting(MeetingEvent meetingEvent) {
        //Filter out the meeting rooms having same time and remove it because of colliding times
        List<MeetingDAO> filteredMeetingDAORooms = meetingRepository.findAll().stream().filter(
                item -> item.getStartTime() >= meetingEvent.getStartTime() && item.getEndTime() <= meetingEvent.getEndTime()
        ).collect(Collectors.toList());
        filteredMeetingDAORooms.stream().forEach(meetingDAO -> meetingRepository.deleteById(meetingDAO.getId()));

        //insert new Admin Meeting
        MeetingDAO meetingDAO = meetingSchedulerService.bookMeeting(meetingEvent);
        meetingRepository.save(meetingDAO);
        return meetingDAO;
    }

}
