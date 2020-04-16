package com.yohanbernole.lamzone.service;

import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.model.MeetingRoom;
import com.yohanbernole.lamzone.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = MeetingGenerator.generateMeetings();
    private List<MeetingRoom> rooms = MeetingGenerator.generateMeetingRooms();
    private List<User> users = MeetingGenerator.generateUsers();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    public List<MeetingRoom> getMeetingRooms() { return rooms; }

    public List<User> getUsers() { return users; }

    public void removeMeeting(Meeting meeting) { meetings.remove(meeting); }

    public Meeting getMeeting(long id) {
        for(int i = 0; i < meetings.size(); i++){
            if(meetings.get(i).getId() == id){
                return meetings.get(i);
            }
        }
        return null;
    }

    public List<String> getAllEmails() {
        ArrayList<String> emails = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
                emails.add(users.get(i).getEmail());
        }
        return emails;
    }

    public User getUser(String email){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getEmail().equals(email)){
                return users.get(i);
            }
        }
        return null;
    }

    public void createMeeting(long id, String name, Date date, MeetingRoom room, String subject, ArrayList<User> users, int duration){
        Meeting meeting = new Meeting(id, name, date, room, subject, users, duration);
        meetings.add(meeting);
    }

    public void createUser(long id, String name, String email){
        User user = new User(id, name, email);
        users.add(user);
    }

    public List<Meeting> filterMeetingByRoomId(List<Integer> ids){
        ArrayList<Meeting> meetingWithFilterByRooms = new ArrayList<>();
        for(Meeting meeting : meetings) {
            for (int id : ids) {
                if(meeting.getLocation().getId() == id )
                    meetingWithFilterByRooms.add(meeting);
            }
        }
        return meetingWithFilterByRooms;
    }

    public List<Meeting> filterMeetingByRoomId(long id){
        ArrayList<Meeting> meetingWithFilterByRooms = new ArrayList<>();
        for(Meeting meeting : meetings) {
                if(meeting.getLocation().getId() == id )
                    meetingWithFilterByRooms.add(meeting);
            }
        return meetingWithFilterByRooms;
    }

    public List<Meeting> filterMeetingByDate(int year, int month, int day){
        ArrayList<Meeting> meetingWithFilterByDate = new ArrayList<>();
        int year2, month2, day2;
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        for(int i = 0; i < meetings.size(); i++){
            cal.setTime(meetings.get(i).getDate());
            year2 = cal.get(Calendar.YEAR); month2 = cal.get(Calendar.MONTH); day2 = cal.get(Calendar.DAY_OF_MONTH);
            if(year == year2 && month == month2 && day == day2){
                meetingWithFilterByDate.add(meetings.get(i));
            }
        }
        return meetingWithFilterByDate;
    }

}
