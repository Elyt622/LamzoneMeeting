package com.yohanbernole.lamzone.service;

import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.model.MeetingRoom;
import java.util.List;


/**
 * Meeting API client
 */
public interface MeetingApiService {

        List<Meeting> getMeetings();

        List<MeetingRoom> getMeetingRooms();

        void removeMeeting(long index);

        Meeting getMeeting(long id);
}
