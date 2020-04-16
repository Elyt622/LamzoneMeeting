package com.yohanbernole.lamzone;

import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.model.MeetingRoom;
import com.yohanbernole.lamzone.model.User;
import com.yohanbernole.lamzone.service.MeetingApiService;
import com.yohanbernole.lamzone.service.MeetingGenerator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class LamzoneMeetingUnitTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }


    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = MeetingGenerator.MEETINGS;
        assertTrue(expectedMeetings.containsAll(meetings));
    }

    @Test
    public void getMeetingRoomsWithSuccess() {
        List<MeetingRoom> rooms = service.getMeetingRooms();
        List<MeetingRoom> expectedRooms = MeetingGenerator.MEETINGROOMS;
        assertTrue(expectedRooms.containsAll(rooms));

    }

    @Test
    public void getUsers() {
        List<User> users = service.getUsers();
        List<User> expectedUsers = MeetingGenerator.USERS;
       assertTrue(expectedUsers.containsAll(users));
    }

    @Test
    public void removeMeeting() {
        List<Meeting> meetings = service.getMeetings();
        Meeting meeting = service.getMeetings().get(0);
        assertTrue(meetings.contains(meeting));
        service.removeMeeting(meeting);
        assertFalse(meetings.contains(meeting));
    }

    @Test
    public void getMeetingWithSuccess() {
        Meeting meeting = service.getMeetings().get(0);
        Meeting meeting1 = service.getMeeting(meeting.getId());
        assertEquals(meeting, meeting1);
    }

    @Test
    public void getUserWithSuccess() {
        User user = service.getUsers().get(0);
        User user1 = service.getUser(user.getEmail());
        assertEquals(user, user1);
    }

    @Test
    public void getAllEmailsWithSuccess() {
        List<String> emails = service.getAllEmails();
        for(User user : service.getUsers()){
            assertTrue(emails.contains(user.getEmail()));
        }
    }

    @Test
    public void createMeeting() {
        service.createMeeting(22, "Reunion test", new Date(1586627538), service.getMeetingRooms().get(0), "Test", new ArrayList<>(Arrays.asList(service.getUsers().get(1), service.getUsers().get(3))), 120);
        assertTrue(service.getMeetings().contains(service.getMeeting(22)));
    }

    @Test
    public void createUserWithSuccess() {
        service.createUser(20, "Test", "test@mail.com");
        assertTrue(service.getUsers().contains(service.getUser("test@mail.com")));
    }

    @Test
    public void filterMeetingByRoomIdWithSuccess() {
        List<Meeting> meetings = service.filterMeetingByRoomId(service.getMeetingRooms().get(0).getId());
        for(Meeting meeting : meetings){
            for (MeetingRoom room : service.getMeetingRooms()){
                if (room == service.getMeetingRooms().get(0)){
                    assertEquals(meeting.getLocation(), room);
                }else{
                    assertNotEquals(meeting.getLocation(), room);
                }
            }
        }
    }

    @Test
    public void filterMeetingByDateWithSuccess() {
        int year = 1970, month = 0, dayOfMonth = 1;
        List<Meeting> meetings = service.filterMeetingByDate(year, month, dayOfMonth);
        Calendar calendar = Calendar.getInstance(); Calendar calendar1 = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, 0,0); calendar1.set(year, month, dayOfMonth+1, 0, 0);
        Date date = calendar.getTime(); Date date1 = calendar1.getTime();
        for (Meeting meeting : meetings) {
            assertTrue(meeting.getDate().after(date));
            assertTrue(meeting.getDate().before(date1));
        }
    }
}