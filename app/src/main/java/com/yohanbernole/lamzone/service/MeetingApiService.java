package com.yohanbernole.lamzone.service;

import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.model.MeetingRoom;
import com.yohanbernole.lamzone.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Meeting API client
 */
public interface MeetingApiService {

        List<Meeting> getMeetings();

        List<MeetingRoom> getMeetingRooms();

        List<User> getUsers();

        void removeMeeting(Meeting meeting);

        Meeting getMeeting(long id);

        User getUser(String email);

        List<String> getAllEmails();

        void createMeeting(long id, String name, Date hours, MeetingRoom room, String subject, ArrayList<User> users, int duration);

        void createUser(long id, String name, String email);

        List<Meeting> filterMeetingByRoomId(List<Integer> ids);

        List<Meeting> filterMeetingByRoomId(long id);

        ArrayList<Meeting> filterMeetingByDate(int year, int month, int day);
}
