package com.yohanbernole.lamzone.service;

import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.model.MeetingRoom;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = MeetingGenerator.generateMeetings();
    private List<MeetingRoom> rooms = MeetingGenerator.generateMeetingRooms();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    public List<MeetingRoom> getMeetingRooms() { return rooms; }

    public void removeMeeting(Meeting meeting) { meetings.remove(meeting); }

    public Meeting getMeeting(long id) {
        for(int i = 0; i < meetings.size(); i++){
            if(meetings.get(i).getId() == id){
                return meetings.get(i);
            }
        }
        return null;
    }
}
