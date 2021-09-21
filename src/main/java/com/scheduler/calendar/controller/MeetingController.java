package com.scheduler.calendar.controller;

import com.scheduler.calendar.model.MeetingDAO;
import com.scheduler.calendar.model.MeetingEvent;
import com.scheduler.calendar.model.MeetingResponse;
import com.scheduler.calendar.model.User;
import com.scheduler.calendar.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("calendar")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    /**
     * Requirement 1 : Create Meeting
     * @param meetingEvent
     * @return
     */
    @PostMapping
    public ResponseEntity<MeetingDAO> scheduleMeeting(@RequestBody MeetingEvent meetingEvent) {
        MeetingDAO meetingDAO = meetingService.scheduleMeeting(meetingEvent);
        return new ResponseEntity<>(meetingDAO, HttpStatus.OK);
    }

    /**
     * Requirement 1 : Update Meeting
     * @param meetingEvent
     * @return
     */
    @PutMapping("{meetingId}")
    public ResponseEntity<MeetingDAO> updateMeeting(@PathVariable String meetingId, @RequestBody MeetingEvent meetingEvent) {
        MeetingDAO meetingDAO = meetingService.updateMeeting(meetingId, meetingEvent);
        return new ResponseEntity<>(meetingDAO, HttpStatus.OK);
    }

    /**
     * Requirement 1 : Delete Meeting
     * @param meetingId MeetingId
     * @return
     */
    @DeleteMapping("{meetingId}")
    public ResponseEntity<String> deleteMapping(@PathVariable String meetingId) {
        meetingService.deleteMeeting(meetingId);
        return new ResponseEntity<>("Meeting Canceled", HttpStatus.OK);
    }

    /**
     * Requirement 3 : USER ACCEPT DECLINE POST -> Lets any user to accept or decline a particular meeting
     *
     * @param meetingId MeetingId
     * @param userId UserId
     * @param meetingResponse MeetingResponse
     * @return
     */
    @PostMapping("/meetingResponse")
    public ResponseEntity<User> addUserMeetingResponse(@RequestParam String meetingId, @RequestParam String userId, @RequestBody MeetingResponse meetingResponse) {
        User user = meetingService.addUserMeetingResponse(meetingId, meetingResponse.getUserResponse(), userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Requirement 3 : USER ACCEPT DECLINE GET
     * @param id MeetingId
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<List<User>> getInviteesDetails(@PathVariable String id) {
        return new ResponseEntity<>(meetingService.getInviteesDetails(id), HttpStatus.OK);
    }

    /**
     * Requirement 2 : Get MeetingDetails
     * @param startTime StartTime
     * @param endTime EndTime
     * @return
     */
    @GetMapping("/meetingDetails")
    public ResponseEntity<List<MeetingDAO>> getMeetingDetails(@RequestParam long startTime, @RequestParam long endTime) {
        return new ResponseEntity<>(meetingService.getMeetingDetails(startTime, endTime), HttpStatus.OK);
    }

    /**
     * Requirement 6 : Available Rooms within a specific time
     * @param startTime StartTime
     * @param endTime EndTime
     * @return List of Available Rooms
     */
    @GetMapping
    public ResponseEntity<List<String>> availableMeetingRooms(@RequestParam long startTime, @RequestParam long endTime){
        return new ResponseEntity<>(meetingService.availableMeetingRooms(startTime, endTime), HttpStatus.OK);
    }

    /**
     * Requirement 5 : Admin Create Meeting
     * @param meetingEvent MeetingEvent
     * @return
     */
    @PostMapping("/admin")
    public ResponseEntity<MeetingDAO> adminScheduleMeeting(@RequestBody MeetingEvent meetingEvent) {
        MeetingDAO meetingDAO = meetingService.adminScheduleMeeting(meetingEvent);
        return new ResponseEntity<>(meetingDAO, HttpStatus.OK);
    }

}
